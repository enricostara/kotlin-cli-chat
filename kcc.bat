@rem #################
@rem    NOT TESTED!
@rem #################

for /f "delims=" %%x in (build\resources\main\version.properties) do (set "%%x")
java -jar build\libs\kotlin-cli-chat-%version%.jar %*