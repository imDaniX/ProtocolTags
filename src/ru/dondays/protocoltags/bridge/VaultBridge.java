package ru.dondays.protocoltags.bridge;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultBridge {

    private static Permission permission;
    private static Chat chat;

    public static void setupHooks() {
        permission = hookPermissions();
        chat = hookChat();
    }

    public static Permission getPermission() {
        return permission;
    }

    public static Chat getChat() {
        return chat;
    }

    private static Permission hookPermissions() {
        Permission permission = null;
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServicesManager().getRegistration(Permission.class);
        if(permissionProvider != null) permission = permissionProvider.getProvider();
        if(permission != null && permission.getName().equals("SuperPerms")) permission = null;
        return permission;
    }

    private static Chat hookChat() {
        Chat chat = null;
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServicesManager().getRegistration(Chat.class);
        if(chatProvider != null) chat = chatProvider.getProvider();
        return chat;
    }
}
