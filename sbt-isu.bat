SET PATH=%PATH%;C:\Program Files\Java\jdk1.8.0_92\bin
echo %PATH%
sbt -Dhttp.proxyHost="proxy.isu.ru" -Dhttp.proxyPort="3128" -Dhttps.proxyHost="proxy.isu.ru" -Dhttps.proxyPort="3128" -Dhttp.nonProxyHosts="localhost|127.0.0.1"