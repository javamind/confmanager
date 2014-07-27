/**
 * Controller linked to the env list
 */
angular.module('confman').controller('applicationCtrl', function ($rootScope, $scope, $modal,$location, Application) {

    //Page definition
    $rootScope.currentPage = {
        name : 'Applications',
        description : 'List of yours apps',
        icon : 'ic_settings_24px'
    };

    //Load environments
    $scope.applications = Application.query();

    //Actions
    $scope.update =  function (elt){
        $location.path('/application/'+ elt.id);
    };
    $scope.create =  function (){
        $location.path('/application/'+0);
    };

});

angular.module('confman').controller('applicationDetailCtrl', function ($rootScope, $scope, $modal, modalConfirmDeleteCtrl, $routeParams,  Application) {

    //Page definition
    $rootScope.currentPage = {
        name : 'Application',
        description : $routeParams.id>0 ? 'Update Application' : 'Create new application',
        icon : 'ic_settings_24px'
    };


    //Load environments
    if($routeParams.id>0){
        $scope.application = Application.get({id:$routeParams.id});
    }
    else{
        $scope.application = Application.query();
    }

});

