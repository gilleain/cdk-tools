Command-line tools based on the Chemistry Development Kit ([CDK](https://github.com/cdk/cdk)), to replace the [old command-line apps](http://sourceforge.net/p/cdk/svn/12731/tree/cdk-clapps/trunk/src/main/org/openscience/cdk/applications/).

To package individual commands:
mvn package -pl core,match
mvn package -pl core,depict
