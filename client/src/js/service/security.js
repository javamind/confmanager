angular.module('confman').factory('Session', function () {
  this.create = function (login, firstName, lastName, email, userRoles) {
    this.login = login;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.userRoles = userRoles;
  };
  this.invalidate = function () {
    this.login = null;
    this.firstName = null;
    this.lastName = null;
    this.email = null;
    this.userRoles = null;
  };
  return this;
});

angular.module('confman').factory('AuthenticationSharedService', ['$rootScope', '$http', '$translate', 'authService', 'Session', 'LanguageService', 'constants',
  function ($rootScope, $http, $translate, authService, Session, LanguageService, constants) {

    function login(param) {
      var data = "username=" + encodeURIComponent(param.username) + "&password=" + encodeURIComponent(param.password) + "&_spring_security_remember_me=" + param.rememberMe + "&submit=Login";
      $http
        .post(constants.urlserver + 'app/authentication', data, {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          ignoreAuthModule: 'ignoreAuthModule'
        })
        .success(function (datazz) {
          $http.get(constants.urlserver + 'app/account')
            .success(
            function (data) {
              Session.create(data.login, data.firstName, data.lastName, data.email, data.roles);
              $rootScope.account = Session;
              authService.loginConfirmed(data);
              $rootScope.callbackOK();
              $translate.use(data.langKey);
              $rootScope.language = data.langKey;
            }
          );
        })
        .error(function () {
          $rootScope.authenticationError = true;
          Session.invalidate();
        });
    };

    function valid(authorizedRoles) {
      $http.get('protected/authentication_check.gif', { ignoreAuthModule: 'ignoreAuthModule'})
        .success(function (data) {
          if (!Session.login) {
            $http.get(constants.urlserver + 'app/account')
              .success(function (data) {
                Session.create(data.login, data.firstName, data.lastName, data.email, data.roles);
                $rootScope.account = Session;
                $rootScope.authenticated = true;
              }
            );
          }
          $rootScope.authenticated = !!Session.login;
        })
        .error(function (data) {
          $rootScope.authenticated = false;

          if (!$rootScope.isAuthorized(authorizedRoles)) {
            $rootScope.$broadcast('event:auth-loginRequired', data);
          }
        }
      );
    };

    function isAuthorized(authorizedRoles) {
      if (!angular.isArray(authorizedRoles)) {
        if (authorizedRoles == '*') {
          return true;
        }
        authorizedRoles = [authorizedRoles];
      }

      var isAuthorized = false;
      angular.forEach(authorizedRoles, function (authorizedRole) {
        var authorized = (!!Session.login &&
          Session.userRoles.indexOf(authorizedRole) !== -1);
        if (authorized || authorizedRole == '*') {
          isAuthorized = true;
        }
      });

      return isAuthorized;
    };

    function isAuthorizedToModify(env) {
      if (!env.profile || Session.userRoles.indexOf(env.profile) !== -1) {
        return true;
      }
      return false;
    };

    function logout() {
      $rootScope.authenticationError = false;
      $rootScope.authenticated = false;
      $rootScope.account = null;

      $http.get(constants.urlserver + 'app/logout');
      Session.invalidate();
      authService.loginCancelled();
    };

    return {
      'login': login,
      'valid': valid,
      'isAuthorized': isAuthorized,
      'isAuthorizedToModify': isAuthorizedToModify,
      'logout': logout
    };
  }]);