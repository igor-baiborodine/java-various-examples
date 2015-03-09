#####The following development standards are used for all new Java projects starting March 2015:

####Code style
- [Google Java Style](https://google-styleguide.googlecode.com/svn/trunk/javaguide.html)
- [intellij-java-google-style.xml](https://github.com/igor-baiborodine/java-various-examples/blob/master/intellij-java-google-style.xml)
 
####Tests
- Class naming: unit tests - {ClassUnderTest}UnitTest, persistence tests - {ClassUnderTest}PersistenceTest, integration tests - {ClassUnderTest}IntegrationTest
- Method naming: \[unitOfWork_stateUnderTest_expectedBehavior\], [read more...](http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html)
- Code layout:
```
@Test
public void sum_simpleValues_calculated() {

    //given
      
    //when
  
    //then
 
}
```
 
