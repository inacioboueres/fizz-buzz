var app = angular.module('fizzBuzzApp', ['ngRoute', 'ngAnimate','ngResource' ]);
 
app.config(function($routeProvider) {
	$routeProvider.when('/plus', {
		templateUrl: 'plus.html',
		controller: 'PlusController'
	}).when('/classic', {
		templateUrl: 'classic.html',
		controller: 'ClassicController'
	}).when('/classicln', {
		templateUrl: 'classic.html',
		controller: 'ClassicLNController'
	}) .otherwise({
	    redirectTo: '/'

	});
	
}).run(function($rootScope,$timeout ) {
	
	$rootScope.messeges=[];
	  
	$rootScope.addMessege = function(message, messageClass, strong) {
		var message = { message: message, messageClass: messageClass, strong: strong };
		$rootScope.messeges.push(message)

		// Simulate 2 seconds loading delay
		$timeout(function() {

			// Loadind done here - Show message for 3 more seconds.
			$timeout(function() {
				$rootScope.messeges.splice(0,1);
			}, 3000);

        }, 2000);
    };
	
	
});	 

app.controller('ClassicController', function($scope, $rootScope, $http) {
	$scope.classicSearch = {
			numbers : "",
		};
	
	$scope.teste = '';
	$scope.result = '';
	$scope.title = 'Classic';
	 
	$scope.login = function() {
		$scope.classicSearch.numbers = $scope.teste;
		var param = angular.toJson($scope.classicSearch);
		$http({
			method : "GET",
			url : 'classicFast/'+$scope.teste,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(_success, _error);
	};
	
	function _success(response) {
		$scope.result = response.data.result;
		$rootScope.addMessege("Choose others numbers and play again", "alert-success", "Congratulations! ");
	}

	function _error(response) {
		$rootScope.addMessege(response.data.error, "alert-danger", "Error: ");
	}
});

app.controller('ClassicLNController', function($scope, $rootScope, $http) {
	$scope.classicSearch = {
			numbers : "",
		};
	
	$scope.teste = '';
	$scope.result = '';
	$scope.title = 'Classic Large Numbers';
	 
	$scope.login = function() {
		$scope.classicSearch.numbers = $scope.teste;
		var param = angular.toJson($scope.classicSearch);
		$http({
			method : "GET",
			url : 'classicBigNumber/'+$scope.teste,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(_success, _error);
	};
	
	function _success(response) {
		$scope.result = response.data.result;
		$rootScope.addMessege("Choose others numbers and play again", "alert-success", "Congratulations! ");
	}

	function _error(response) {
		$rootScope.addMessege(response.data.error, "alert-danger", "Error: ");
	}
});

app.controller('PlusController', function($scope, $rootScope, $http) {
	console.log('plus');
	$scope.orders = [{
	      	'id': 'U',
	      	'desc': 'Unordered '
	    }, {
	    	'id': 'A',
		    'desc': 'Ascending '
	    }, {
	    	'id': 'D',
		    'desc': 'Descending '
	    }];
	$scope.types = [{
	      	'id': 'D',
	      	'desc': 'Divisible '
	    }, {
	    	'id': 'R',
		    'desc': 'Repetition '
	    }, {
	    	'id': 'B',
		    'desc': 'Both '
	    }];
	$scope.rules = [{
	      	'id': '3',
	      	'desc': 'Fizz '
	    }, {
	    	'id': '5',
		    'desc': 'Buzz '
	    }];
	
	$scope.orderId = 'U';
	$scope.typeId = 'D';
	$scope.classicSearch = {
			numbers : "",
		};
	
	$scope.teste = '';
	$scope.result = '';
	$scope.title = 'Custom';
	
	
	$scope.newRule = function(){

        $scope.rules.push({id:$scope.ruleNumber, desc:$scope.ruleDesc});

        $scope.ruleNumber = '';
        $scope.ruleDesc = '';

    };
    
    
    $scope.removeRule = function(item){ 
      var index=$scope.rules.indexOf(item)
      $scope.rules.splice(index,1);     
    }
	 
	$scope.login = function() {
		$scope.classicSearch.numbers = $scope.teste;
		var param = angular.toJson($scope.classicSearch);
		$http({
			method : "GET",
			url : 'classicBigNumber/'+$scope.teste,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(_success, _error);
	};
	
	function _success(response) {
		$scope.result = response.data.result;
		$rootScope.addMessege("Choose others numbers and play again", "alert-success", "Congratulations! ");
	}

	function _error(response) {
		$rootScope.addMessege(response.data.error, "alert-danger", "Error: ");
	}
});