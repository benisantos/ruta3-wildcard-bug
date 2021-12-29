
# ruta3-wildcard-bug

This is a sample project to reproduce a likely bug with Ruta 3 (and Ruta 2.8.x) wildcard matching condition (#).


## Description

Suppose there is a rule such as:

```
t1:MyType.featX=="foo" # t2:MyType.featX=="_pending"{-> SETFEATURE("featX", t1.featX)};
```

If there is other annotation of type `MyType` with `featX!="foo"` between t1 and t2, all feats captured in the `t1` variable are null.

It works with Ruta 2.6.1 and 2.7.0.

**UPDATE**: If the condition `t2:MyType.featX=="_pending"` is changed by `t2:MyType{FEATURE("featX", "_pending")}`, it works!


## Steps to reproduce the problem

Install maven 3.x. Go to the project root folder and type:

```
mvn test
```

The test fails with this message:

```
Failed tests:   testFooBar(ruta3.NaiveCorefTest): Person name of 'himself' is null

Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
```


**Note**: It was tested with AdoptOpenJDK 1.8.0_252 and Apache Maven 3.3.9.

