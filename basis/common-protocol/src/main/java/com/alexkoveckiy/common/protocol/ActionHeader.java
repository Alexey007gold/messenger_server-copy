package com.alexkoveckiy.common.protocol;

public class ActionHeader {

	private String uuid;
    private String originUuid;
    private String command;
    private String type;
    private String version;

    public ActionHeader() {
    }

    public ActionHeader(String uuid, String originUuid, String command, String type, String version) {
        this.uuid = uuid;
        this.originUuid = originUuid;
        this.command = command;
        this.type = type;
        this.version = version;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOriginUuid() {
        return originUuid;
    }

    public void setOriginUuid(String originUuid) {
        this.originUuid = originUuid;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
