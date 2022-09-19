@ECHO OFF

echo Building gradle project...
pause
call gradle build
echo .
echo .

echo Making jar copy from "build\libs" to "output\" and replacing old file version(s)...
echo Exit this window to avoid this process.
echo .
pause 
echo .

if exist build\libs\ (
  echo Found build source "build\libs\" ...
  echo . 
) else (
  echo There is no folder "build\libs\" to find builds, bye.
  echo .
  exit 1
)
if exist output\ (
  goto docopy
) else (
  echo output folder not found, creating one...
  echo .
  goto makedir
)
:makedir
mkdir output
echo Created folder "output\"
echo .
goto docopy
:docopy
copy build\libs output\. /y

pause
exit 1

