SET DEVELOPMENT_HOME=%CD%

cd %DEVELOPMENT_HOME%\ks-parent
call mvn clean install source:jar deploy

cd %DEVELOPMENT_HOME%\ks-commons
call mvn clean install source:jar deploy -Dmaven.test.skip=true

cd %DEVELOPMENT_HOME%\ks-client
call mvn clean install source:jar deploy -Dmaven.test.skip=true

cd %DEVELOPMENT_HOME%\ks-server
call mvn clean install source:jar deploy -Dmaven.test.skip=true

pause