package com.reactnativecommunity.webview;

import java.util.*;

public class GrantedPermissionManager {
  private Map<String, Set<String>> map; // key: host, value: granted permissions

  public GrantedPermissionManager() {
    map = new HashMap<>();
  }

  // use synchronized to make thread-safe
  public synchronized void add(String host, List<String> permissions) {
    if (host == null || host.isEmpty() || permissions == null || permissions.isEmpty()) {
      return;
    }
    if (!map.containsKey(host)) {
      map.put(host, new HashSet<>());
    }
    Set<String> grantedPermissions = map.get(host);
    grantedPermissions.addAll(getValidPermissions(permissions));
  }

  // use synchronized to make thread-safe
  public synchronized boolean containsAll(String host, List<String> permissions) {
    if (host == null || host.isEmpty() || permissions == null || permissions.isEmpty()) {
      return false;
    }
    return map.containsKey(host) &&
        map.get(host).containsAll(getValidPermissions(permissions));
  }

  private List<String> getValidPermissions(List<String> permissions) {
    List<String> cleaned = new ArrayList<>();
    if (permissions == null || permissions.isEmpty()) {
      return cleaned;
    }
    for (String permission : permissions) {
      if (permission != null && !permission.isEmpty()) {
        cleaned.add(permission);
      }
    }
    return cleaned;
  }
}
