set projectDir=C:\wp\jwebunit\jWebunit
set cpath=%projectDir%\classes;%projectDir%\lib\fit.jar;%projectDir%\lib\httpunit.jar;%projectDir%\lib\js.jar;%projectDir%\lib\junit.jar;%projectDir%\lib\servlet.jar;%projectDir%\lib\nekohtml.jar;%projectDir%\lib\xercesImpl.jar;%projectDir%\lib\xml-apis.jar
java -cp %cpath% -Dwiki=true net.sourceforge.jwebunit.fit.DirectoryRunner %1 %2