#Android Learning Series: Mockito

###Why to use Mockito?

Mockito is a powerful way to mock our dependencies in order to allow us to unit test our code.

It does allow us to have a simple way to provide some pre defined behaviour in order to see if our code is being executed properly.

In this project, we can see the example using the LoginTest class, where we test if the login flow is properly working. This Unit Test consist of two different components: the UserRegistration class, responsible for handling the whole user registration flow, checking if the user is already registered (or not) and throwing exceptions (in case it does exists, it throws UserAlreadyExistException). As well we do have the interface Database, used by UserRegistration class. This interface is responsible for providing to us the base method used by the UserRegistration class, such as hasUser, add, etc.

In this example, we do want to mock our class **Database** in order to simulate the scenario where the user is already registered. Looking inside the UserRegistration class, it does call the method **hasUser** of the class database, and based on the return of it (boolean value), it does throw the exception (or not).

What we want to achieve is exactly this scenario, where the user already exists on the database. How can we proceed in order to have such scenario? Simply by mocking our Database object as shown
in the setup() method in the LoginTest class. Following example:

```
public class LoginTest {

    private Database database;

    @Before
    public void setup() {
        database = Mockito.mock(Database.class);
    }
}
```

*or we could do in the following way using the @Mock annotation before the field we want to mock*

```
public class LoginTest {
	@Mock
	private Database database;
}
```


This will simply mock our database class for us. Now that we have it, the behaviour we want to throw our exception is to make the method **hasUser** return true, so the **UserAlreadyExist** exception will be thrown. We can do it in the following way (also described on the method shouldThrowUserAlreadyRegisteredException in the LoginTest class).

```
@Test(expected = UserAlreadyRegisteredException.class)
   public void shouldThrowUserAlreadyRegisteredException() throws UserAlreadyRegisteredException {

       UserRegistration userRegistration = new UserRegistration(mockedDatabase);

       when(mockedDatabase.hasUser(anyString())).thenReturn(true);

       userRegistration.registerNewUser("rodrigo.bresan@destinationEmail.com");
   }
```

You can notice we have the call for the method **when()**, that was statically imported from the package
org.mockito.Mockito. With this method, we are telling to Mockito that when we call the method hasUser with anyString as parameter (also static method from Mockito), this method will return us the boolean value true. Well, that is all that we want, don't we? So well, this way we can be sure that our database object will behave in the way that we told it to behave, throwing a exception of already registered user and making our test pass successfully.

This is also a point that why we should use Dependency Injection pattern into our code base, since
if we initialized our Database inside our UserRegistration method, it would be wya harder for us
to write the code for it, since this dependency would be directly inside the code, and not received
through the constructor (or through any other Dependency Injection framework) you would use. Keep in mind that we are trying to test only the UserRegistration class in this test, not the database.
The point in unit testing is to isolate the component in order to achieve the test of only a single component, and not it's dependencies.

======

###What makes a good unit test?

####Being fast

We want our Unit Tests to be fast, so for us not having any Network calls or I/O operations (such as reading files, databases, etc) that can make it to be delayed and slowed down. We need to have in mind that our projects may have thousands of unit tests on it, so we need a test suit that can run fast, preferably in a matter of seconds and not relying on third party components.

####Being deterministic

The test should fail 100% of time or pass 100% of time. Tests that don't follow this standard are called **flakey** tests, and they can undermine our confidence in our test suit.

####Being self-contained

Tests shouldn't rely on any other tests. As well it shouldn't rely in any external state (such as the result of any other test). Also shouldn't rely on a database being populated in a certain way
if other tests might alter it.

####Being focused

As the own name says, unit tests should be testing only a unit of the code. Don't make test for a class and all of its dependencies at the same time. Just interact with a small number of classes,
being pretty often just the one you are testing.

#### Test Doubles

Use a test double instead of a database object, otherwise tests could be:

**Slow:** network or I/O operations can be time

**Nondeterministic**: it could fail one time and it could pass on

**Not self-contained:** since you are relaying on another

**Not focused:** we would be testing also the database implementation and all of its dependencies


**Umbrella term:** any object which is used as a substitute instead of a real dependency for unit testing

Think of this as a Java Interface.

###Dummy Object

Object passed that is never used, just for filling parameters. Just for compiler being happy :-)

###Fake Object

Real working implementation. It is not supposed to be a code that should be working in production
code. Example: memory data instead of using a real database.

###Stub Object

Configured to provide hardcoded responses. Only under certain conditions. E.x.:

when() method is a example, since it does return a stub once it meets a certain condition.

####Why use a mock?

1. To modify the behaviour of one object
2. To specify in which ways the mock will be interacted with:
    a. which methods will be called
    b. in which order
    c. how many times (maybe never)

Using the method verify on the Mockito class we can check if a specific method of our mock was called
or not.

We can use the same object as many entities, such as a stub and a mock. Example:

On the following call, the object will be used as a stub, since it will be returning a hardcoded
value based on a certain scenario.

```
Mockito.when(yourMock.yourMethod()).thenReturn(false);
```
In the following one, you will see we are just verifying if the method was called or not, so it's
simply a mock.

```
Mockito.verify(yourMock).yourMethod();
```

#Creating stubs

So far we have been using the Mockito.when method, but we can also use the method

```
doReturn(value).when(yourMock).yourMethod(anyString())
```

**Mockito.when() only work for methods that return something**, not workable for void methods. If you try
to stub a method that doesn't have a return type (is void), you will receive the
CannotStubVoidMethodWithReturnValue exception. For these cases (without return method) we should use
the following syntax:

Let't say we have the method delete, without any return type. We would do in the following way:

```
Mockito.doThrow(UserNotFoundException.class).when(mockDatabase).deleteUser(destinationEmail);

// and then call userRegistration.delete(destinationEmail) to test it.
```

Which syntax to use?

It's a matter of personal preference. The benefit of the doThrow method is that it does allow the usage
even for non returning methods. But for readability the easier for understanding (in my opinion :D)
is the when/then. In any way, compiler will keep you on the right track (about trying to do it on
void return methods).

Default return methods provided by Mockito:

- primitives (int, boolean);
- wrapper around primitives (Integer);
- collections (ArrayList);

======

###Matchers

anyString()

Let's say you want Mockito to check when a method is called, but this with any different String. How
could you do it? You could do it as follows:

```
when(mockDatabase.hasUser(anyString()).thenReturn(true);
```

With this call, the method hasUser will accept any kind of string you specify. If you want to assert
null values, you need to use the ArgumentMatchers.any() method, casted to a String object.

We do have many different matchers, such as anyDouble, anyFloat, anyBoolean, anyMap, etc..

=====


Pay attention to the method verify, since we need to specify the mock and then later the method we
are calling, not both together. Example:

```
Mockito.verify(mockDatabase).addUser(emailAddress); //RIGHT
Mockito.verify(mockDatabase.addUser(emailAddress)); //WRONG
```

It will throw NotAMockException :-)


======

Also we can use Mockito with our android tests, as shown in the file **SecondActivityTest.class**. There
you can see we are testing if the intent values are being properly filled. Pretty handful for us
when we start dealing with big screens that receive lots of values and that in some scenarios may
lead us to write buggy code.









