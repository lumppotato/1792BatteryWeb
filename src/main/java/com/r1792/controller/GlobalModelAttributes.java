package com.r1792.controller;

import com.r1792.service.AppInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final AppInfo appInfo;

    public GlobalModelAttributes(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    @ModelAttribute("version")
    public String version() {
        return appInfo.getVersion();
    }
}
