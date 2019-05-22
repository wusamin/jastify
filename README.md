# jastify
jastify is java library for Spotify Web API.

this libaray calls spotify web api and returns json mapped POJO.
I aim to be as simple as possible to access spotify.

## Usage
First, create instance of jastify by builder pattern.

```
Jastify jastify = new Jastify.Builder()
                         .clientID("your client id")
                         .clientSecret("your client secret")
                         .refreshToken("your refresh token")
                         .userID("your userID")
                         .build();
```

Or, you can get these parameter from jastify.properties or any .properties file.

```
# jastify.properties or foo.properties
spotify.token=foo
spotify.refreshToken=bar
spotify.clientID=baz
spotify.clientSecret=qux
spotify.code=quux
spotify.userID=foobar
```


```
// from jastify.properties
Jastify jastify = new Jastify.Builder()
                         .load()
                         .build();

// from foo.properties
Jastify jastify = new Jastify.Builder()
                         .load("foo.properties")
                         .build();


```