del *.iml
rmdir .gradle /S /Q
del local.properties
cd app
rmdir build /Q /S
cd ..
cd .idea
rmdir caches /Q /S
rmdir libraries /S /Q
del modules.xml
del workspace.xml
rem del navEditor.xml
rem del assetWizardSettings.xmlr
cd ..