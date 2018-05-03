var app = angular.module('listApp',[]);
    
    app.config(function($provide){
            
        $provide.factory("transFormFactory",function(){
            return {
                transForm : function(obj){
                    var str = [];  
                    for(var p in obj){  
                      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
                    } 
                    return str.join("&");  
                }
            };
        });
    });
    
  app.controller('ListController', function($scope,$http,transFormFactory) {
    var list = this;
    list.currentPage = 1;
    list.totalPages=3;
    list.unsalegoods =  [
        {id:'00001', text:'learn AngularJS', img:'asserts/images/lunch.jpeg', price:'199'},
        {id:'00002', text:'build an AngularJS', img:'asserts/images/lunch.jpeg', price:'199'},
        {id:'00003', text:'build an AngularJS', img:'asserts/images/lunch.jpeg', price:'199'}
        ];
    list.onsalegoods =  [
        {id:'00001', text:'learn AngularJS', img:'asserts/images/lunch.jpeg', price:'199'},
        {id:'00002', text:'build an AngularJS', img:'asserts/images/lunch.jpeg', price:'199'},
        {id:'00003', text:'build an AngularJS', img:'asserts/images/lunch.jpeg', price:'299'}
        ];
    
    (function(){
    	
    	$scope.url =  "service.do";
    	var postdata = {'mode':'list'};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
    		}).then(function (result) {
    			list.onsalegoods = result.data.onsalegoods;
    			list.unsalegoods = result.data.unsalegoods;
            }).catch(function (result) {
            	list.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
        
    })();
    
    list.unsale = function(id) {
    	$scope.url =  "service.do";
    	var postdata = {'mode':'unsale', 'id': id};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
    		}).then(function (result) {
    			list.onsalegoods = result.data.onsalegoods;
    			list.unsalegoods = result.data.unsalegoods;
            }).catch(function (result) {
            	orderList.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
    }
    
    list.onsale = function(id) {
    	$scope.url =  "service.do";
    	var postdata = {'mode':'onsale', 'id': id};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
    		}).then(function (result) {
    			list.onsalegoods = result.data.onsalegoods;
    			list.unsalegoods = result.data.unsalegoods;
            }).catch(function (result) {
            	orderList.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
    }
  });