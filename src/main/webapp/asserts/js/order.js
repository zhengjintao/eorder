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
    orderList.orders = [
        {id:'00001', text:'learn AngularJS', img:'asserts/images/lunch.jpeg', price:'199',  amount:'1'},
        {id:'00002', text:'build an AngularJS app', img:'asserts/images/lunch2.jpeg', price:'299', amount:'2'}];
    
    orderList.goods = [
    	{row:[
             {id:'00001', text:'learn AngularJS', img:'asserts/images/lunch.jpeg', price:'199'},
             {id:'00002', text:'build an AngularJS app', img:'asserts/images/lunch.jpeg', price:'199'},
             {id:'00003', text:'build an AngularJS app', img:'asserts/images/lunch.jpeg', price:'199'}
             ]
    	},
        {row:[
            {id:'00004', text:'learn AngularJS', img:'asserts/images/lunch6.jpeg', price:'299'},
            {id:'00005', text:'build an AngularJS app', img:'asserts/images/lunch6.jpeg', price:'299'},
            {id:'00006', text:'build an AngularJS app', img:'asserts/images/lunch6.jpeg', price:'299'}
            ]
        },
        {row:[
                {id:'00007', text:'learn AngularJS', img:'asserts/images/lunch.jpeg', price:'199'},
                {id:'00008', text:'build an AngularJS app', img:'asserts/images/lunch.jpeg', price:'199'},
                {id:'00009', text:'build an AngularJS app', img:'asserts/images/lunch.jpeg', price:'199'}
             ]}
    ];
    
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
    };
    
    orderList.makeOrder = function() {
    	//window.demo.javaMethod();
    	
    	$scope.url =  "makeorder.do";
    	var postdata = {'orders':JSON.stringify(orderList.orders)};
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
  });