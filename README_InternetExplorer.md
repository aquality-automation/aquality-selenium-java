IE requirements to start and use browser via selenium webdriver:

1. In IE Internet options -> Security, all Zones (Internet - Local - Trusted) must have the same protection level.
Also Enable Protected Mode must be set to the same value (enabled or disabled) for all zones.

2. For IE 11 only, you will need to set a registry entry on the target computer so that
the driver can maintain a connection to the instance of Internet Explorer it creates.
For 32-bit Windows installations, the key you must examine in the registry editor is
HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Internet Explorer\Main\FeatureControl\FEATURE_BFCACHE.
For 64-bit Windows installations, the key is HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Internet
Explorer\Main\FeatureControl\FEATURE_BFCACHE. Please note that the FEATURE_BFCACHE
subkey may or may not be present, and should be created if it is not present. Important:
Inside this key, create a DWORD value named iexplore.exe with the value of 0.

Here you need to specify DWORD value named iexplore.exe with the value of 0 has to
be set both for 64bit and 32 bit Windows.

3. If you see the warning message like this: "WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5."
You may have to create a key named "Prefs" at registry path HKEY_LOCAL_MACHINE\Software\JavaSoft and/or HKEY_LOCAL_MACHINE\Software\WOW6432Node\JavaSoft

4. In some cases popup for saving password can disturb test execution. We recommended to disable it.
You can do this via Internet options -> Content -> Settings (in the AutoComplete section) -> uncheck the "Ask me before saving passwords" and "User names and passwords on forms"