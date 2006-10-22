ant dist
mkdir dist;mkdir dist/release
cp ../appfuse*.zip dist/release

cd ..
rm -rf appfuse-spring-dist
mkdir appfuse-spring-dist
cp -r appfuse appfuse-spring-dist/.
cd appfuse-spring-dist/appfuse
rm -rf extras/myjavapack extras/jsf extras/tapestry extras/webwork
cd extras/spring;ant install;cd ../..;rm -rf extras/spring
ant dist -Darchive.prefix=appfuse-springmvc -Dnodocs=true
cp ../*.zip ../../appfuse/dist/release

cd ../..
rm -rf appfuse-webwork-dist
mkdir appfuse-webwork-dist
cp -rf appfuse appfuse-webwork-dist/.
cd appfuse-webwork-dist/appfuse
rm -rf extras/myjavapack extras/jsf extras/spring extras/tapestry
cd extras/webwork;ant install;cd ../..;rm -rf extras/webwork
ant dist -Darchive.prefix=appfuse-webwork -Dnodocs=true
cp ../*.zip ../../appfuse/dist/release

cd ../..
rm -rf appfuse-jsf-dist
mkdir appfuse-jsf-dist
cp -rf appfuse appfuse-jsf-dist/.
cd appfuse-jsf-dist/appfuse
rm -rf extras/myjavapack extras/spring extras/tapestry extras/webwork
cd extras/jsf;ant install;cd ../..;rm -rf extras/jsf
ant dist -Darchive.prefix=appfuse-jsf -Dnodocs=true
cp ../*.zip ../../appfuse/dist/release

cd ../..
rm -rf appfuse-tapestry-dist
mkdir appfuse-tapestry-dist
cp -rf appfuse appfuse-tapestry-dist/.
cd appfuse-tapestry-dist/appfuse
rm -rf extras/myjavapack extras/jsf extras/spring extras/webwork
cd extras/tapestry;ant install;cd ../..;rm -rf extras/tapestry
ant dist -Darchive.prefix=appfuse-tapestry -Dnodocs=true
cp ../*.zip ../../appfuse/dist/release

