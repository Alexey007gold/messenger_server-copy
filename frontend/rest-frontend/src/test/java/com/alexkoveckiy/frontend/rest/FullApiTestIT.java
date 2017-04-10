package com.alexkoveckiy.frontend.rest;

import com.alexkoveckiy.authorization.api.message.*;
import com.alexkoveckiy.authorization.impl.configuration.AuthorizationImplConfig;
import com.alexkoveckiy.common.dao.configuration.PersistentJPAConfig;
import com.alexkoveckiy.common.dao.service.BaseService;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.datamapper.configuration.DataMapperConfig;
import com.alexkoveckiy.profile.api.dto.ContactDTO;
import com.alexkoveckiy.profile.api.dto.ProfileStatusDTO;
import com.alexkoveckiy.profile.api.dto.UserProfileDTO;
import com.alexkoveckiy.common.exceptions.InvalidTokenException;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.modelmapper.configuration.CommonModelMapperConfig;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.router.configuration.CommonRouterConfig;
import com.alexkoveckiy.common.router.impl.FirstRouter;
import com.alexkoveckiy.common.token.api.TokenHandler;
import com.alexkoveckiy.common.token.configuration.TokenConfig;
import com.alexkoveckiy.messenger.impl.configuration.MessengerImplConfig;
import com.alexkoveckiy.profile.api.message.*;
import com.alexkoveckiy.profile.impl.configuration.ProfileImplConfig;
import com.alexkoveckiy.wssession.configuration.WSSessionServiceConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static com.google.common.collect.Range.greaterThan;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by alex on 04.04.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DataMapperConfig.class,
        CommonRouterConfig.class,
        AuthorizationImplConfig.class,
        ProfileImplConfig.class,
        MessengerImplConfig.class,
        PersistentJPAConfig.class,
        TokenConfig.class,
        CommonModelMapperConfig.class,
        WSSessionServiceConfig.class
})
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
public class FullApiTestIT {

    @Autowired
    private FirstRouter firstRouter;

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private ModelMapperService modelMapperService;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private List<BaseService> services;
    
    private List<String> names;

    @Before
    public void init() {
        for (BaseService service : services)
            service.deleteAll();

        names = new ArrayList<>();
        names.add("Jamison Courtright");
        names.add("Tommye Fitzhugh");
        names.add("Saul Lacey");
        names.add("Numbers Mclane");
        names.add("Luna Crittenden");
        names.add("Anastacia Sillman");
        names.add("Darin Aslinger");
        names.add("Xuan Bolton");
        names.add("Billy Temples");
        names.add("Lucila Mcdougald");
        names.add("Lucien Leachman");
        names.add("Mika Witman");
        names.add("Faustina Gaynor");
        names.add("Elbert Waters");
        names.add("Hank Wohlgemuth");
        names.add("Tomas Whitham");
        names.add("Nan Weymouth");
        names.add("Anna Boggess");
        names.add("Amberly Kleinman");
        names.add("Abe Kellar");
        names.add("Dexter Aragon");
        names.add("Cris Veneziano");
        names.add("Domenica Mcilvaine");
        names.add("Bonita Evert");
        names.add("Pat Sheeley");
        names.add("Merna Parcell");
        names.add("Kayleigh Danna");
        names.add("Arletha Shires");
        names.add("Monika Verdejo");
        names.add("Leesa Ballin");
        names.add("Pearlene Rosenthal");
        names.add("Chad Blas");
        names.add("Tonda Thomure");
        names.add("Kary Spigner");
        names.add("Jaleesa Witcher");
        names.add("Yuette Rigby");
        names.add("Lesia Dunn");
        names.add("Cora Rubinstein");
        names.add("Stasia Rizzuto");
        names.add("Casie Sundstrom");
        names.add("Natacha Rubi");
        names.add("Hyo Rickards");
        names.add("Casandra Bounds");
        names.add("Rich Weatherholt");
        names.add("Theo Bones");
        names.add("Winford Majors");
        names.add("Ivan Murrin");
        names.add("Byron Gunnell");
        names.add("Venessa Kistner");
        names.add("Clora Kieffer");
    }

    @Test
    public void test() throws InvalidTokenException {
        //create few registration sessions
        List<Response<RegisterResponse>> registerResponses = new ArrayList<>();
        List<String> phoneNumbers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            phoneNumbers.add(generatePhoneNumber());
            registerResponses.add(testRegistrationHandler(phoneNumbers.get(i), "device" + i + "_1"));
        }

        //check that registration on the same number from same device is impossible as timeout did not ran out yet
        testRegisterIsNotSuccessful(getRegisterRequest(phoneNumbers.get(2), "device2_1"));

        //but from another device it's possible
        Response<RegisterResponse> registerResponse2_2 = testRegistrationHandler(phoneNumbers.get(2), "device2_2");

        //finish all registration sessions with sms confirmation
        List<Response<SmsConfirmResponse>> smsConfirmResponses = new ArrayList<>();
        for (Response<RegisterResponse> registerResponse : registerResponses)
            smsConfirmResponses.add(testSmsConfirmHandler(registerResponse));
        Response<SmsConfirmResponse> smsConfirmResponse2_2 = testSmsConfirmHandler(registerResponse2_2);

        //register two more devices for 4th user
        Response<SmsConfirmResponse> smsConfirmResponse4_2 = testSmsConfirmHandler(testRegistrationHandler(phoneNumbers.get(4), "device4_2"));
        Response<SmsConfirmResponse> smsConfirmResponse4_3 = testSmsConfirmHandler(testRegistrationHandler(phoneNumbers.get(4), "device4_3"));

        //login all and get routing data
        List<Response<LoginResponse>> loginResponses = new ArrayList<>();
        List<RoutingData> routingDataList = new ArrayList<>();
        for (int i = 0; i < smsConfirmResponses.size(); i++) {
            loginResponses.add(testLoginHandler(smsConfirmResponses.get(i)));
            routingDataList.add(tokenHandler.getRoutingDataFromTemporaryToken(loginResponses.get(i).getData().getToken()));
        }
        Response<LoginResponse> loginResponse2_2 = testLoginHandler(smsConfirmResponse2_2);
        RoutingData routingData2_2 = tokenHandler.getRoutingDataFromTemporaryToken(loginResponse2_2.getData().getToken());
        Response<LoginResponse> loginResponse4_2 = testLoginHandler(smsConfirmResponse4_2);
        RoutingData routingData4_2 = tokenHandler.getRoutingDataFromTemporaryToken(loginResponse4_2.getData().getToken());
        Response<LoginResponse> loginResponse4_3 = testLoginHandler(smsConfirmResponse4_3);
        RoutingData routingData4_3 = tokenHandler.getRoutingDataFromTemporaryToken(loginResponse4_3.getData().getToken());

        syncContacts(phoneNumbers, loginResponses, routingDataList);

        //get my profiles and check phone number correctness
        List<Response<GetMyProfileResponse>> getMyProfileResponses = getResponses(phoneNumbers, loginResponses, routingDataList, routingData2_2, routingData4_2, routingData4_3);

        testGetContactProfiles(loginResponses, routingDataList, routingData2_2, routingData4_2, routingData4_3, getMyProfileResponses);

        testGetOtherProfiles(loginResponses, routingDataList, routingData2_2, routingData4_2, routingData4_3, getMyProfileResponses);

        List<String> avatarUris = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> statuses = new ArrayList<>();
        testSetMyProfile(loginResponses, routingDataList, routingData2_2, routingData4_3, avatarUris, names, statuses);

        testGetMyProfile(phoneNumbers, loginResponses, routingDataList, routingData2_2, routingData4_2, routingData4_3, avatarUris, names, statuses);

        testMyProfileSettings(loginResponses, routingDataList, routingData2_2, routingData4_3);

    }

    private void testMyProfileSettings(List<Response<LoginResponse>> loginResponses, List<RoutingData> routingDataList, RoutingData routingData2_2, RoutingData routingData4_3) {
        for (int i = 0; i < loginResponses.size(); i++)
            testGetProfileSettings(routingDataList.get(i), true, true);

        //setMyProfileSettings
        for (int i = 0; i < loginResponses.size(); i++) {
            if (i != 2 && i != 4)
                testSetProfileSettings(routingDataList.get(i), false, false);
        }
        testSetProfileSettings(routingData2_2, false, false);
        testSetProfileSettings(routingData4_3, false, false);
        //getMyProfileSettings
        for (int i = 0; i < loginResponses.size(); i++)
            testGetProfileSettings(routingDataList.get(i), false, false);
        //setMyProfileSettings
        for (int i = 0; i < loginResponses.size(); i++)
            testSetProfileSettings(routingDataList.get(i), true, false);
        //getMyProfileSettings
        for (int i = 0; i < loginResponses.size(); i++) {
            if (i != 2 && i != 4)
                testGetProfileSettings(routingDataList.get(i), true, false);
        }
        testGetProfileSettings(routingData2_2, true, false);
        testGetProfileSettings(routingData4_3, true, false);
        //setMyProfileSettings
        for (int i = 0; i < loginResponses.size(); i++)
            testSetProfileSettings(routingDataList.get(i), true, true);
        //getMyProfileSettings
        for (int i = 0; i < loginResponses.size(); i++)
            testGetProfileSettings(routingDataList.get(i), true, true);
    }

    private void testGetMyProfile(List<String> phoneNumbers, List<Response<LoginResponse>> loginResponses, List<RoutingData> routingDataList, RoutingData routingData2_2, RoutingData routingData4_2, RoutingData routingData4_3, List<String> avatarUris, List<String> names, List<String> statuses) {
        for (int i = 0; i < loginResponses.size(); i++)
            testGetMyProfileHandlerWithSetts(routingDataList.get(i), phoneNumbers.get(i), avatarUris.get(i), names.get(i), statuses.get(i));
        testGetMyProfileHandlerWithSetts(routingData2_2, phoneNumbers.get(2), avatarUris.get(2), names.get(2), statuses.get(2));
        testGetMyProfileHandlerWithSetts(routingData4_2, phoneNumbers.get(4), avatarUris.get(4), names.get(4), statuses.get(4));
        testGetMyProfileHandlerWithSetts(routingData4_3, phoneNumbers.get(4), avatarUris.get(4), names.get(4), statuses.get(4));
    }

    private void testSetMyProfile(List<Response<LoginResponse>> loginResponses, List<RoutingData> routingDataList, RoutingData routingData2_2, RoutingData routingData4_3, List<String> avatarUris, List<String> names, List<String> statuses) {
        for (int i = 0; i < loginResponses.size(); i++) {
            avatarUris.add(generateRandomString());
            names.add(generateRandomString());
            statuses.add(generateRandomString());
            if (i != 2 && i != 4)
                testSetMyProfileHandler(routingDataList.get(i), avatarUris.get(i), names.get(i), statuses.get(i));
        }
        testSetMyProfileHandler(routingData2_2, avatarUris.get(2), names.get(2), statuses.get(2));
        testSetMyProfileHandler(routingData4_3, avatarUris.get(4), names.get(4), statuses.get(4));
    }

    private void testGetOtherProfiles(List<Response<LoginResponse>> loginResponses, List<RoutingData> routingDataList, RoutingData routingData2_2, RoutingData routingData4_2, RoutingData routingData4_3, List<Response<GetMyProfileResponse>> getMyProfileResponses) {
        for (int i = 0; i < loginResponses.size(); i++)
            testGetOtherProfilesHandler(getMyProfileResponses, routingDataList.get(i));
        testGetOtherProfilesHandler(getMyProfileResponses, routingData2_2);
        testGetOtherProfilesHandler(getMyProfileResponses, routingData4_2);
        testGetOtherProfilesHandler(getMyProfileResponses, routingData4_3);
    }

    private void testGetContactProfiles(List<Response<LoginResponse>> loginResponses, List<RoutingData> routingDataList, RoutingData routingData2_2, RoutingData routingData4_2, RoutingData routingData4_3, List<Response<GetMyProfileResponse>> getMyProfileResponses) {
        for (int i = 0; i < loginResponses.size(); i++)
            testGetContactProfilesHandler(getMyProfileResponses, getMyProfileResponses.get(i), routingDataList.get(i));
        testGetContactProfilesHandler(getMyProfileResponses, getMyProfileResponses.get(2), routingData2_2);
        testGetContactProfilesHandler(getMyProfileResponses, getMyProfileResponses.get(4), routingData4_2);
        testGetContactProfilesHandler(getMyProfileResponses, getMyProfileResponses.get(4), routingData4_3);
    }

    private List<Response<GetMyProfileResponse>> getResponses(List<String> phoneNumbers, List<Response<LoginResponse>> loginResponses, List<RoutingData> routingDataList, RoutingData routingData2_2, RoutingData routingData4_2, RoutingData routingData4_3) {
        List<Response<GetMyProfileResponse>> getMyProfileResponses = new ArrayList<>();
        for (int i = 0; i < loginResponses.size(); i++)
            getMyProfileResponses.add(testGetMyProfileHandlerWithEmptySetts(routingDataList.get(i), phoneNumbers.get(i)));
        testGetMyProfileHandlerWithEmptySetts(routingData2_2, phoneNumbers.get(2));
        testGetMyProfileHandlerWithEmptySetts(routingData4_2, phoneNumbers.get(4));
        testGetMyProfileHandlerWithEmptySetts(routingData4_3, phoneNumbers.get(4));
        return getMyProfileResponses;
    }

    private void syncContacts(List<String> phoneNumbers, List<Response<LoginResponse>> loginResponses, List<RoutingData> routingDataList) {
        List<List<ContactDTO>> contacts = new ArrayList<>();
        for (int i = 0; i < loginResponses.size(); i++) {
            contacts.add(getContacts(phoneNumbers, i));
            testContactsSyncHandler(contacts.get(i), routingDataList.get(i));
        }
    }

    private List<ContactDTO> getContacts(List<String> phoneNumbers, int i) {
        List<ContactDTO> contacts = new ArrayList<>();
        Set<String> numbers;
        if (i % 2 == 0) {
            numbers = new HashSet<>();
            numbers.add(generatePhoneNumber());
            numbers.add(generatePhoneNumber());
            Collections.shuffle(names);
            contacts.add(new ContactDTO(names.remove(0), numbers));
        }
        for (int j = 0; j < 6; j++) {
            if (j != i) {
                numbers = new HashSet<>();
                numbers.add(phoneNumbers.get(j));
                if (j % 3 == 0) {
                    numbers.add(generatePhoneNumber());
                    numbers.add(generatePhoneNumber());
                }
                contacts.add(new ContactDTO("user_" + j, numbers));
            }
        }
        return contacts;
    }

    private Response<RegisterResponse> testRegistrationHandler(String phoneNumber, String deviceId) {
        return testRegistrationHandler(getRegisterRequest(phoneNumber, deviceId));
    }

    private Response<RegisterResponse> testRegistrationHandler(Request<RegisterRequest> request) {
        Response<RegisterResponse> response = (Response<RegisterResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getRegistrationRequestUuid().length(), is(36));
        assertThat(response.getData().getRegistrationTimeoutSec(), is(notNullValue(Integer.class)));
        assertThat(response.getData().getAuthCode(), is(notNullValue(Integer.class)));
        return response;
    }

    private Response<RegisterResponse> testRegisterIsNotSuccessful(Request<RegisterRequest> request) {
        Response<RegisterResponse> response = (Response<RegisterResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getData(), is(nullValue()));
        assertThat(response.getStatus().getCode(), not(200));
        return response;
    }

    private Response<SmsConfirmResponse> testSmsConfirmHandler(Response<RegisterResponse> registerResponse) {
        return testSmsConfirmHandler(getSmsConfirmRequest(registerResponse));
    }

    private Response<SmsConfirmResponse> testSmsConfirmHandler(Request<SmsConfirmRequest> request) {
        Response<SmsConfirmResponse> response = (Response<SmsConfirmResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getDeviceToken(), is(notNullValue(String.class)));
        return response;
    }

    private Response<LoginResponse> testLoginHandler(Response<SmsConfirmResponse> smsConfirmResponse) {
        return testLoginHandler(getLoginRequest(smsConfirmResponse));
    }

    private Response<LoginResponse> testLoginHandler(Request<LoginRequest> request) {
        Response<LoginResponse> response = (Response<LoginResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getToken(), is(notNullValue(String.class)));
        return response;
    }

    private void testContactsSyncHandler(List<ContactDTO> contacts, RoutingData routingData) {
        testContactsSyncHandler(getContactsSyncRequest(contacts, routingData));
    }

    private void testContactsSyncHandler(Request<ContactSyncRequest> request) {
        Response<ContactSyncResponse> response = (Response<ContactSyncResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
    }

    private void testGetContactProfilesHandler(List<Response<GetMyProfileResponse>> getMyProfileResponses, Response<GetMyProfileResponse> absentProfile, RoutingData routingData) {
        testGetContactProfilesHandler(getGetContactProfilesRequest(routingData), absentProfile, getMyProfileResponses);
    }

    private void testGetContactProfilesHandler(Request<GetContactProfilesRequest> request, Response<GetMyProfileResponse> absentProfile, List<Response<GetMyProfileResponse>> getMyProfileResponses) {
        Response<GetContactProfilesResponse> response = (Response<GetContactProfilesResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getProfiles().size(), is(5));
        assertThat(checkProfileCorresponding(response.getData().getProfiles(), modelMapperService.map(absentProfile.getData().getProfile(), UserProfileDTO.class), getMyProfileResponses), is(true));
    }

    private void testGetOtherProfilesHandler(List<Response<GetMyProfileResponse>> getMyProfileResponses, RoutingData routingData) {
        testGetOtherProfilesHandler(getGetOtherProfilesRequest(getMyProfileResponses, routingData), getMyProfileResponses, routingData);
    }

    private void testGetOtherProfilesHandler(Request<GetOtherProfilesRequest> request, List<Response<GetMyProfileResponse>> getMyProfileResponses, RoutingData routingData) {
        Response<GetOtherProfilesResponse> response = (Response<GetOtherProfilesResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getProfiles().size(), is(6));
        checkProfileCorresponding(response.getData().getProfiles(), getMyProfileResponses);
    }

    private Response<GetMyProfileResponse> testGetMyProfileHandlerWithEmptySetts(RoutingData routingData, String phoneNumber) {
        return testGetMyProfileHandlerWithEmptySetts(getGetMyProfileRequest(routingData), phoneNumber);
    }

    private Response<GetMyProfileResponse> testGetMyProfileHandlerWithEmptySetts(Request<GetMyProfileRequest> request, String phoneNumber) {
        Response<GetMyProfileResponse> response = (Response<GetMyProfileResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getProfile().getPhoneNumber(), is(phoneNumber));
        assertThat(response.getData().getProfile().getAvatarUri(), nullValue(String.class));
        assertThat(response.getData().getProfile().getName(), nullValue(String.class));
        assertThat(response.getData().getProfile().getStatus(), nullValue(String.class));
        return response;
    }

    private void testGetMyProfileHandlerWithSetts(RoutingData routingData, String phoneNumber, String avatarUri, String name, String status) {
        testGetMyProfileHandlerWithSetts(getGetMyProfileRequest(routingData), phoneNumber, avatarUri, name, status);
    }

    private void testGetMyProfileHandlerWithSetts(Request<GetMyProfileRequest> request, String phoneNumber, String avatarUri, String name, String status) {
        Response<GetMyProfileResponse> response = (Response<GetMyProfileResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getProfile().getPhoneNumber(), is(phoneNumber));
        assertThat(response.getData().getProfile().getAvatarUri(), is(avatarUri));
        assertThat(response.getData().getProfile().getName(), is(name));
        assertThat(response.getData().getProfile().getStatus(), is(status));
        assertThat(response.getData().getProfile().getCreateDateTime(), not(999));
        assertThat(response.getData().getProfile().getUpdateDateTime(), not(777));
    }

    private void testSetMyProfileHandler(RoutingData routingData, String avatarUri, String name, String status) {
        testSetMyProfileHandler(getSetMyProfileRequest(routingData, avatarUri, name, status));
    }

    private void testSetMyProfileHandler(Request<SetMyProfileRequest> request) {
        Response<SetMyProfileResponse> response = (Response<SetMyProfileResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
    }

    private void testSetProfileSettings(RoutingData routingData, boolean shareOnline, boolean shareSeen) {
        testSetProfileSettings(getSetProfileSettingsRequest(routingData, shareOnline, shareSeen));
    }

    private void testSetProfileSettings(Request<SetProfileSettingsRequest> request) {
        Response<SetProfileSettingsResponse> response = (Response<SetProfileSettingsResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
    }

    private void testGetProfileSettings(RoutingData routingData, boolean shareOnline, boolean shareSeen) {
        testGetProfileSettings(getGetProfileSettingsRequest(routingData), shareOnline, shareSeen);
    }

    private void testGetProfileSettings(Request<GetProfileSettingsRequest> request, boolean shareOnline, boolean shareSeen) {
        Response<GetProfileSettingsResponse> response = (Response<GetProfileSettingsResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getSettings().isShareOnlineStatus(), is(shareOnline));
        assertThat(response.getData().getSettings().isShareSeenStatus(), is(shareSeen));
    }

    private void testGetLastTimeOnlineHandler(List<Response<GetMyProfileResponse>> getMyProfileResponses, RoutingData routingData) {
        testGetLastTimeOnlineHandler(getGetLastTimeOnlineRequest(getMyProfileResponses, routingData));
    }

    private void testGetLastTimeOnlineHandler(Request<GetLastTimeOnlineRequest> request) {
        Response<GetLastTimeOnlineResponse> response = (Response<GetLastTimeOnlineResponse>) firstRouter.handle(request);
        checkResponseHeaderIsOk(request, response);
        assertThat(response.getStatus().getCode(), is(200));
        assertThat(response.getData().getProfiles().size(), is(6));
        for (ProfileStatusDTO profileStatusDTO : response.getData().getProfiles()) {
            assertThat(profileStatusDTO.isOnline(), is(true));
            assertThat(profileStatusDTO.getLastTimeOnline(), is(greaterThan(System.currentTimeMillis() - 1000)));
        }
    }

    private void checkResponseHeaderIsOk(Request<?> request, Response<?> response) {
        assertThat(response.getHeader().getUuid().length(), is(36));
        assertThat(response.getHeader().getOriginUuid(), is(request.getHeader().getUuid()));
        assertThat(response.getHeader().getCommand(), is(request.getHeader().getCommand()));
        assertThat(response.getHeader().getType(), is(request.getHeader().getType()));
    }

    private boolean checkProfileCorresponding(List<UserProfileDTO> presentProfiles, UserProfileDTO absentProfile, List<Response<GetMyProfileResponse>> getMyProfileResponses) {
        return !(profilePresent(absentProfile, presentProfiles) || !checkProfileCorresponding(presentProfiles, getMyProfileResponses));
    }

    private boolean checkProfileCorresponding(List<UserProfileDTO> profiles, List<Response<GetMyProfileResponse>> getMyProfileResponses) {
        List<UserProfileDTO> listForChecking = new ArrayList<>();
        for (Response<GetMyProfileResponse> getMyProfileResponse : getMyProfileResponses)
            listForChecking.add(modelMapperService.map(getMyProfileResponse.getData().getProfile(), UserProfileDTO.class));

        for (UserProfileDTO profile : profiles) {
            if (!profilePresent(profile, listForChecking))
                return false;
        }
        return true;
    }

    private boolean profilePresent(UserProfileDTO profile, List<UserProfileDTO> listForChecking) {
        for (UserProfileDTO getMyProfileResponse : listForChecking) {
            if (profile.getPhoneNumber().equals(getMyProfileResponse.getPhoneNumber()) &&
                    profile.getCreateDateTime().equals(getMyProfileResponse.getCreateDateTime()) &&
                    profile.getUpdateDateTime().equals(getMyProfileResponse.getUpdateDateTime()) &&
                    Objects.equals(profile.getAvatarUri(), getMyProfileResponse.getAvatarUri()) &&
                    Objects.equals(profile.getName(), getMyProfileResponse.getName()) &&
                    Objects.equals(profile.getStatus(), getMyProfileResponse.getStatus())) {
                return true;
            }
        }
        return false;
    }

    private String generatePhoneNumber() {
        StringBuilder builder = new StringBuilder("+");
        for (int i = 0; i < 12; i++)
            builder.append((int)(Math.random() * 10));
        return builder.toString();
    }

    public String generateRandomString() {
        StringBuilder builder = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 10; i++)
            builder.append((char) (r.nextInt(26) + 'a'));
        return builder.toString();
    }

    private Request<RegisterRequest> getRegisterRequest(String phoneNumber, String deviceId) {
        return dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"authorization\",\n" +
                "\t\"command\": \"register\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\"phone_number\": \"" + phoneNumber + "\",\n" +
                "\t\"locale_code\": \"uk_UA\",\n" +
                "\t\"device_id\": \"" + deviceId + "\"\n" +
                "\t}\n" +
                "}", new TypeReference<Request<RegisterRequest>>() {});
    }

    private Request<SmsConfirmRequest> getSmsConfirmRequest(Response<RegisterResponse> registerResponse) {
        return dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"authorization\",\n" +
                "\t\"command\": \"sms_confirm\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\"auth_code\": \"" + registerResponse.getData().getAuthCode() + "\",\n" +
                "\t\"registration_request_uuid\": \"" + registerResponse.getData().getRegistrationRequestUuid() + "\"\n" +
                "\t}\n" +
                "}", new TypeReference<Request<SmsConfirmRequest>>() {});
    }

    private Request<LoginRequest> getLoginRequest(Response<SmsConfirmResponse> smsConfirmResponse) {
        return dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"authorization\",\n" +
                "\t\"command\": \"login\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\"device_token\": \"" + smsConfirmResponse.getData().getDeviceToken() + "\"\n" +
                "\t}\n" +
                "}", new TypeReference<Request<LoginRequest>>() {});
    }

    private Request<ContactSyncRequest> getContactsSyncRequest(List<ContactDTO> contacts, RoutingData routingData) {
        Request<ContactSyncRequest> request = dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"profile\",\n" +
                "\t\"command\": \"contacts_sync\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\"added_contacts\": " + dataMapper.dataToString(contacts) + "\n" +
                "\t}\n" +
                "}", new TypeReference<Request<ContactSyncRequest>>() {});
        request.setRoutingData(routingData);
        return request;
    }

    private Request<GetContactProfilesRequest> getGetContactProfilesRequest(RoutingData routingData) {
        Request<GetContactProfilesRequest> request = dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"profile\",\n" +
                "\t\"command\": \"get_contact_profiles\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t}\n" +
                "}", new TypeReference<Request<GetContactProfilesRequest>>() {});
        request.setRoutingData(routingData);
        return request;
    }

    public Request<GetMyProfileRequest> getGetMyProfileRequest(RoutingData routingData) {
        Request<GetMyProfileRequest> request = dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"profile\",\n" +
                "\t\"command\": \"get_my_profile\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t}\n" +
                "}", new TypeReference<Request<GetMyProfileRequest>>() {});
        request.setRoutingData(routingData);
        return request;
    }

    public Request<GetOtherProfilesRequest> getGetOtherProfilesRequest(List<Response<GetMyProfileResponse>> getMyProfileResponses, RoutingData routingData) {
        List<String> ids = new ArrayList<>();
        for (Response<GetMyProfileResponse> getMyProfileResponse : getMyProfileResponses)
            ids.add(getMyProfileResponse.getData().getProfile().getId());

        Request<GetOtherProfilesRequest> request = dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"profile\",\n" +
                "\t\"command\": \"get_other_profiles\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\"user_id\": " + dataMapper.dataToString(ids) + "\n" +
                "\t}\n" +
                "}", new TypeReference<Request<GetOtherProfilesRequest>>() {});
        request.setRoutingData(routingData);
        return request;
    }

    private Request<SetMyProfileRequest> getSetMyProfileRequest(RoutingData routingData, String avatarUri, String name, String status) {
        Request<SetMyProfileRequest> request = dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"profile\",\n" +
                "\t\"command\": \"set_my_profile\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t},\n" +
                "\t\"data\": {\n" +
                "\t\t\"profile\": {\n" +
                "\t\t\t\"phone_number\": \"" + "+000000000000" + "\",\n" +
                "\t\t\t\"create_date_time\": " + 999 + ",\n" +
                "\t\t\t\"update_date_time\": " + 777 + ",\n" +
                "\t\t\t\"avatar_uri\": \"" + avatarUri + "\",\n" +
                "\t\t\t\"name\": \"" + name + "\",\n" +
                "\t\t\t\"status\": \"" + status + "\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", new TypeReference<Request<SetMyProfileRequest>>() {});
        request.setRoutingData(routingData);
        return request;
    }

    private Request<GetProfileSettingsRequest> getGetProfileSettingsRequest(RoutingData routingData) {
        Request<GetProfileSettingsRequest> request = dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"profile\",\n" +
                "\t\"command\": \"get_profile_settings\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t}\n" +
                "}", new TypeReference<Request<GetProfileSettingsRequest>>() {});
        request.setRoutingData(routingData);
        return request;
    }

    private Request<SetProfileSettingsRequest>  getSetProfileSettingsRequest(RoutingData routingData, boolean shareOnline, boolean shareSeen) {
        Request<SetProfileSettingsRequest> request = dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"profile\",\n" +
                "\t\"command\": \"set_profile_settings\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t},\n" +
                "\t\"data\": {\n" +
                "\t\t\"settings\": {\n" +
                "\t\t\t\"share_online_status\": " + shareOnline + ",\n" +
                "\t\t\t\"share_seen_status\": " + shareSeen + "\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", new TypeReference<Request<SetProfileSettingsRequest>>() {});
        request.setRoutingData(routingData);
        return request;
    }

    public Request<GetLastTimeOnlineRequest> getGetLastTimeOnlineRequest(List<Response<GetMyProfileResponse>> getMyProfileResponses, RoutingData routingData) {
        List<String> ids = new ArrayList<>();
        for (Response<GetMyProfileResponse> getMyProfileResponse : getMyProfileResponses)
            ids.add(getMyProfileResponse.getData().getProfile().getId());

        Request<GetLastTimeOnlineRequest> request = dataMapper.mapData("{\"header\": {\n" +
                "\t\"type\": \"profile\",\n" +
                "\t\"command\": \"get_last_time_online\",\n" +
                "\t\"uuid\": \"" + UUID.randomUUID().toString() + "\"\n" +
                "\t},\n" +
                "\"data\": {\n" +
                "\t\"profiles\": " + dataMapper.dataToString(ids) + "\n" +
                "\t}\n" +
                "}", new TypeReference<Request<GetLastTimeOnlineRequest>>() {});
        request.setRoutingData(routingData);
        return request;
    }
}
