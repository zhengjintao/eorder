var app = angular.module('orderApp',[]);
    
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
    
  app.controller('OrderListController', function($scope,$http,transFormFactory) {
    var orderList = this;
    orderList.currentPage = 1;
    orderList.totalPages=3;
    orderList.orders = [];
    orderList.goods =  [
    	{row:[
            {id:'00001', text:'learn AngularJS', img:'asserts/images/lunch.jpeg', price:'199'},
            {id:'00002', text:'build an AngularJS', img:'asserts/images/lunch.jpeg', price:'199'},
            {id:'00003', text:'build an AngularJS', img:'asserts/images/lunch.jpeg', price:'199'}
            ]
   	}
   ];
    
    (function(){
    	
    	$scope.url =  "service.do";
    	var postdata = {'mode':'init', 'pageIndex': '1'};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded'}
    		}).then(function (result) {
    			orderList.goods = result.data.goods;
    			orderList.currentPage = result.data.currentPage;
    			orderList.totalPages = result.data.totalPages;
            }).catch(function (result) {
            	orderList.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
        
    })();
    
    orderList.getGoodsTotalValue = function() {
    	var total = 0;
        angular.forEach(orderList.orders, function(order) {
        	total += parseInt(order.price) * parseInt(order.amount);
        });
        return total;
    };
    
    orderList.goodsTotalValue = orderList.getGoodsTotalValue();
    
    orderList.getGoodsCount = function() {
    	var total = 0;
        angular.forEach(orderList.orders, function(order) {
        	total += parseInt(order.amount);
        });
        return total;
    };
    orderList.goodsCount = orderList.getGoodsCount();
    orderList.visableLabel = orderList.goodsCount == 0;
    orderList.addTodo = function() {
    	orderList.todos.push({text:orderList.todoText, done:false});
    	orderList.todoText = '';
    };
 
    orderList.remaining = function() {
      var count = 0;
      angular.forEach(orderList.todos, function(todo) {
        count += todo.done ? 0 : 1;
      });
      return count;
    };
 
    orderList.order = function(good) {
    	var exist=false;
    	var oldOrders = orderList.orders;
    	orderList.orders = [];
        angular.forEach(oldOrders, function(order) {
            if (order.id == good.id) {
            	order.amount++;
            	orderList.orders.push(order);
            	exist=true;
            }else{
            	orderList.orders.push(order);
            }
        });
        if(!exist){
        	orderList.orders.push({id:good.id, text:good.text, img:good.img, price:good.price,  amount:'1'});
        }
        
        orderList.goodsTotalValue = orderList.getGoodsTotalValue();
        orderList.goodsCount = orderList.getGoodsCount();
        orderList.visableLabel = orderList.goodsCount == 0;
    };
    
    orderList.sub = function(id) {
    	var exist=false;
    	var oldOrders = orderList.orders;
    	orderList.orders = [];
        angular.forEach(oldOrders, function(order) {
        	
            if (order.id == id) {
            	order.amount--;
            	if(order.amount != 0){
            		orderList.orders.push(order);
            	}
            }else{
            	orderList.orders.push(order);
            }
        });
        
        orderList.goodsTotalValue = orderList.getGoodsTotalValue();
        orderList.goodsCount = orderList.getGoodsCount();
        orderList.visableLabel = orderList.goodsCount == 0;
    };
      
    orderList.add = function(id) {
    	var exist=false;
    	var oldOrders = orderList.orders;
    	orderList.orders = [];
        angular.forEach(oldOrders, function(order) {
            if (order.id == id) {
            	order.amount++;
            	orderList.orders.push(order);
            	exist=true;
            }else{
            	orderList.orders.push(order);
            }
        });
        if(!exist){
        	orderList.orders.push({id:'00001', text:'learn AngularJS', img:'asserts/images/lunch.jpeg', price:'199'});
        }
        
        orderList.goodsTotalValue = orderList.getGoodsTotalValue();
        orderList.goodsCount = orderList.getGoodsCount();
        orderList.visableLabel = orderList.goodsCount == 0;
    };
    
    orderList.makeOrder = function() {
    	if(orderList.orders.length == 0){
    		orderList.message = "FAILED!商品を一つ以上を選択してください。";
        	$('.ui.basic.modal') .modal('show');
        	return;
    	}
    	//window.demo.javaMethod();
    	
    	$scope.url =  "service.do";
    	var postdata = {'mode':'order','orders':JSON.stringify(orderList.orders)};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded'}
    		}).then(function (result) {
                $scope.content = result.data;
                if(result.data.status=='OK'){
                	orderList.message = "SUCCESS!ありがとうございました。";
                	$('.ui.basic.modal') .modal('show');
                	orderList.orders=[];
                	orderList.goodsTotalValue = orderList.getGoodsTotalValue();
                    orderList.goodsCount = orderList.getGoodsCount();
                }else{
                	orderList.message = "SORRY!エラーが発生しました。";
                	$('.ui.basic.modal') .modal('show');
                }
            }).catch(function (result) {
            	orderList.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
    	
    }
    
    orderList.prev = function(currentPage) {
    	if(currentPage== 1){
    		return;
    	}
    	$scope.url =  "service.do";
    	var postdata = {'mode':'init', 'pageIndex': parseInt(currentPage)-1};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded'}
    		}).then(function (result) {
    			orderList.goods = result.data.goods;
    			orderList.currentPage = result.data.currentPage;
    			orderList.totalPages = result.data.totalPages;
            }).catch(function (result) {
            	orderList.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
    } 
    orderList.next = function(currentPage) {
    	if(currentPage== orderList.totalPages){
    		return;
    	}
    	$scope.url =  "service.do";
    	var postdata = {'mode':'init', 'pageIndex': parseInt(currentPage)+1};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded'},
    		    charset:"UTF_8"
    		}).then(function (result) {
    			orderList.goods = result.data.goods;
    			orderList.currentPage = result.data.currentPage;
    			orderList.totalPages = result.data.totalPages;
            }).catch(function (result) {
            	orderList.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
    }
  });