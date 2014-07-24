/**
 * Controller linked to the application's groupment list
 */
angular.module('confman').controller('softwaresuiteCtrl', function ($rootScope, $scope, $http, $modal, modalConfirmDeleteCtrl, SoftwareSuite, Environment, constants) {

    //Page definition
    $rootScope.currentPage = {
        name : 'Software Suite',
        description : 'In a complex context you have often a set of of software',
        icon : 'ic_settings_24px'
    };


    //Load applicationgroupments
    $scope.softwaresuites = SoftwareSuite.query();


    //Actions
    $scope.update =  function (elt){
        $scope.entity = {
            verb : 'Update software suite',
            content : elt
        };
        $scope.hideEnv=true;
        //Load environments
        $scope.environments = Environment.query(function(){
            $http.get(constants.urlserver + '/softwaresuite/' + elt.id +'/environment')
                .success(function (data) {
                    data.forEach(function(element){
                        //on parcours la liste des env
                        for(i = 0 ; i<$scope.environments.length ; i++){
                            var o = $scope.environments[i];
                            if(o.id===element.idEnvironmentDto){
                                o.selected = true;
                                break;
                            }
                        }
                    });
                    $scope.hideEnv=false;
                })
                .error(function (data) {
                    $rootScope.setError('Error on environments load ');
                });
        });

    };
    $scope.create =  function (){
        $scope.entity = {
            verb : 'Create software suite',
            content : {}
        };
    };
    $scope.delete =  function (elt, $event){
        var modalInstance = $modal.open({
            templateUrl: 'modalConfirmDelete.html',
            controller: modalConfirmDeleteCtrl,
            resolve: {
                entity_todelete : function () {
                    return 'software suite ' + elt.code;
                }
            }
        });
        //callback dans lequel on fait la suppression
        modalInstance.result.then(function (response) {
            $event.stopPropagation();
            SoftwareSuite.delete(
                elt,
                function(data){
                    $scope.error=null;
                    var index = find_entity_index($scope.softwaresuites, elt);
                    if(index>=0) {
                        $scope.softwaresuites.splice(index, 1);
                    }
                    $scope.entity.content = null;
                },
                $scope.callbackKO);
        });
    };
    $scope.save =  function (form){
        if(form.$error.required){
            $rootScope.setError('Your form is not submitted : code and label are required');
            return;
        }
        //We check code existence
        if(verify_code_unicity($scope.softwaresuites, $scope.entity.content)>0){
            $rootScope.setError('The code [' + $scope.entity.content + '] is already in use');
            return;
        }

        if(!$scope.entity.content.id){
            SoftwareSuite.save(
                $scope.entity.content,
                function(data){
                    $rootScope.error=null;
                    if(!$scope.softwaresuites){
                        $scope.softwaresuites = {};
                    }
                    $scope.softwaresuites.push(data);
                    $scope.entity.content = data;
                    $scope.entity.verb = 'Update software suite';
                },
                $scope.callbackKO);
        }
        else{
            $scope.entity.content.$update($scope.callbackOK, $scope.callbackKO);
        }
    };

})

