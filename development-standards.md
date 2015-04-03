####Code style
- [Google Java Style](https://google-styleguide.googlecode.com/svn/trunk/javaguide.html)
- [intellij-java-google-style.xml](https://github.com/igor-baiborodine/java-various-examples/blob/master/intellij-java-google-style.xml)
 
####Tests
- Class naming: unit tests - {ClassUnderTest}UnitTest, persistence tests - {ClassUnderTest}PersistenceTest, integration tests - {ClassUnderTest}IntegrationTest
- Method naming: \[unitOfWork_stateUnderTest_expectedBehavior\], [read more...](http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html)
- Code layout: given-when-then pattern a.k.a. [Arrange-Act-Assert](http://c2.com/cgi/wiki?ArrangeActAssert)
- Assertions: [Hamcrest library](https://code.google.com/p/hamcrest/wiki/Tutorial) 

```
@Test
 public void sum_simpleValues_calculated() {
     
   // given  
   Calculator calculator = new Calculator();

   // when 
   int sum = calculator.sum(1, 3);
   
   // then
   assertThat(sum, is(4));
 }
```

