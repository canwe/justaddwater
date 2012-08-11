# Just Add Water

Just Add Water is an open-source Wicket project that makes it easy to support user logins in your webapp.

If you're starting from scratch you can use this project as a template and build your app around it.
If you have existing code you can simply use it as an example, or copy the relevant parts into your codebase.


It builds on the following projects:

 * [Apache Wicket](http://wicket.apache.org)
 * [Wicket-CDI](https://github.com/42Lines/wicket-cdi)
 * [Twitter Bootstrap](http://twitter.github.com/bootstrap/)

You can try it out at [just-add-water.appspot.com](http://just-add-water.appspot.com).


### Features

 * local accounts with passwords, secured via bcrypt
 * "login with Facebook" authentication, using Facebook OAuth 2.0 (in dev)
 * "change password" page
 * "forgot my password" page
 * one-time-login support (with expiration) via generated tokens


### Installation

You'll first need to build & install wicket-cdi:

 * $ git clone https://github.com/42Lines/wicket-cdi.git
 * $ cd wicket-cdi; mvn clean install

Now you can check out Just Add Water:

 * $ git clone git@github.com:canwe/justaddwater.git

#### Facebook Integration

If you're using Facebook logins you'll need to first [create your app on Facebook](https://developers.facebook.com/apps).

Note that it's helpful during debugging to set your site URL to `localhost:8080` and domain to `localhost`; once
you've got it working locally you can change these settings to the real values.

Once you've done that and generated an App ID and API key, you can add those values to `FacebookOAuthPage.java`.

#### Email Integration

I'm using this https://developers.google.com/appengine/docs/java/mail/usingjavamail.


