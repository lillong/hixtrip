package com.hixtrip.sample.infra.manager;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * lua 脚本管理器
 * @author lill
 */
@Component
public class LuaScriptManager {

    public final static String CHANGE_INVENTORY = "change_inventory.lua";

    private final Map<String, String> scriptCache = new ConcurrentHashMap<>();


    public String getChangeInventory() {
        return this.getScript(CHANGE_INVENTORY);
    }


    public String getScript(String name) {
        return scriptCache.computeIfAbsent(name, this::loadScript);
    }

    private String loadScript(String name) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("lua/" + name)) {
            if (is == null) {
                throw new FileNotFoundException("lua 文件不存在: " + name);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("加载 lua 脚本失败", e);
        }
    }
}
