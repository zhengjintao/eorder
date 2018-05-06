<html>
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">

<!-- Site Properties -->
<title>オーダシステム</title>
<link rel="shortcut icon" type="image/png" href="favicon.ico">
<link rel="stylesheet" type="text/css" href="dist/semantic.min.css">

<style type="text/css">
body {
	-webkit-text-size-adjust: 100%;
	background-color: #FFFFFF;
	min-height: 600px;
	height: 800px;
	min-width: 1000px;
}

.fileInput {
	width: 0.1px;
	height: 0.1px;
	opacity: 0;
	overflow: hidden;
	position: absolute;
	z-index: -1;
}

.cropArea {
	background: #E4E4E4;
	overflow: hidden;
	margin: 0 0 0 0;
	height: 600px;
}
</style>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="dist/semantic.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<script src="ngimgcorp/ng-img-crop.js"></script>
<link rel="stylesheet" type="text/css" href="ngimgcorp/ng-img-crop.css">
<script type="text/javascript">

</script>

<script>
var app = angular.module('app', [ 'ngImgCrop' ]);

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

	app.controller(
			'Ctrl',
			function($scope,$http,transFormFactory) {
				$scope.myImage = '';
				$scope.myCroppedImage = '';

				var handleFileSelect = function(evt) {
					var file = evt.currentTarget.files[0];
					var reader = new FileReader();
					reader.onload = function(evt) {
						$scope.$apply(function($scope) {
							$scope.myImage = evt.target.result;
						});
					};
					reader.readAsDataURL(file);
				};
				angular.element(document.querySelector('#fileInput')).on(
						'change', handleFileSelect);
				
				$scope.price = "0";
				
				$scope.name = "商品名称";
				
				$scope.id = '<%=request.getAttribute("id") %>';
				
				if($scope.id.length >0){
					$scope.url =  "service.do";
			    	var postdata = {'mode':'detailinit', 'id':$scope.id};
			        $http(
			    		{
			    			method:"POST",
			    			url:$scope.url,
			    			data:postdata,
			    			transformRequest:transFormFactory.transForm,
			    			headers:{'Content-Type': 'application/x-www-form-urlencoded'}
			    		}).then(function (result) {
                            $scope.price = result.data.price;
							
							$scope.name = result.data.text;
							
							$scope.id = result.data.id;
							$scope.myImage = result.data.img;
			    		 }).catch(function (result) {
			    			 $scope.message = "SORRY!エラーが発生しました。";
			             	$('.ui.basic.modal') .modal('show');
			             });
				}
				
				function getBlobBydataURL(dataURI,type){
				      var binary = atob(dataURI.split(',')[1]);
				      var array = [];
				      for(var i = 0; i < binary.length; i++) {
				        array.push(binary.charCodeAt(i));
				      }
				      return new Blob([new Uint8Array(array)], {type:type });
				    }
				
				$scope.savegood = function() {
					if($scope.name.length==0){
						$scope.message = "名称は必須入力です。";

			            $('.ui.basic.modal') .modal('show');
						return;
					}
					if($scope.price.length==0){
						$scope.message = "価格は必須入力です。";

			            $('.ui.basic.modal') .modal('show');
						return;
					}
					
					if(!$scope.price.match("^[0-9]{1,9}$") || "0"==$scope.price[0]){
						 $scope.message = "価格は半角数字のみ(9桁以下)を入力してください。";

				            $('.ui.basic.modal') .modal('show');
							return;
					 }
					
					$scope.imgfile=getBlobBydataURL($scope.myCroppedImage,"image/png");
			    	$scope.url =  "service.do";
			    	var postdata = {'mode':'savegood', 'id': $scope.id, 'name': $scope.name,
			    			'price': $scope.price,'img': $scope.myImage, 'imgtext':$scope.myCroppedImage};
			    	
			        $http(
			    		{
			    			method:"POST",
			    			url:$scope.url,
			    			data:postdata,
			    			transformRequest:transFormFactory.transForm,
			    			headers:{'Content-Type': 'application/x-www-form-urlencoded'}
			    		}).then(function (result) {
			    			$scope.message = "1" == result.data.status? "SUCCESS|新商品を追加しました。" : "SUCCESS|商品情報を更新しました。"

			             	$('.ui.basic.modal') .modal('show');
			            }).catch(function (result) {
			            	$scope.message = "SORRY!エラーが発生しました。";
			             	$('.ui.basic.modal') .modal('show');
			            });
			    }
				
			});
</script>

</head>
<body ng-app="app" ng-controller="Ctrl">
	<div class="ui grid"
		style="border: 0px solid #EEEAAA; margin: 0 0 0 0; min-height: 600px">

		<div class="four wide column" style="border: 1px solid #BBBBBB;">
			<div class="ui top attached label"
				style="text-align: center; font-size: 15px">预览</div>
			<div class="ui card" ng-click="orderList.order(good)">
				<input type="hidden" id={{good.id}} value={{good.id}}>
				<div class="image">
					<img ng-src="{{myCroppedImage}}" />
				</div>
				<div class="content center aligned">
					<p class="header" style="margin: 3 0 0 0">{{name}}</p>
					<p class="header right aligned" style="margin: -3 0 0 0">¥{{price}}</p>
				</div>

			</div>
		</div>

		<div class="four wide column" style="border: 1px solid #BBBBBB;">

			<div class="ui container" style="margin-top: 10px">
				<div class="ui top attached label"
					style="text-align: center; font-size: 15px">商品情報</div>


				<div class="ui middle aligned  grid container">
					<div class="ui fluid segment">
						<input type="file"  class="fileInput"
							id="fileInput" /> <label for="fileInput"
							class="ui big right floated button"> <i
							class="ui upload icon"></i> Upload image
						</label>
					</div>

				</div>


				<div class="row" style="margin-top: 20px">
					<form class="ui form">
						<div class="field" ng-show=false>
							<label>ID</label> <input type="text" name="id" placeholder="ID"
								ng-model="id">
						</div>
						<div class="field">
							<label>名称</label> <input type="text" name="name" placeholder="名称（必須入力）"
								ng-model="name">
						</div>
						<div class="field">
							<label>価格</label> <input type="text" name="price"
								placeholder="価格（必須入力、数値）" ng-model="price">
						</div>

						<div class="ui button" tabindex="0" ng-click="savegood()">OK</div>
						<a class="ui button" tabindex="0" href="service.do?mode=listinit">一覧へ戻す</a>
					</form>
				</div>
			</div>
		</div>

		<div class="eight wide column" style="border: 1px solid #BBBBBB;">
			<div class="cropArea" style="border: 1px solid #EEEDDD;">
				<img-crop image="myImage" result-image="myCroppedImage"
					area-type="square"></img-crop>
			</div>
		</div>
		
				<div class="ui basic modal">
		<div class="ui icon header">
			<i class="archive icon"></i>{{message}}
		</div>
		<!-- <div class="content">
			<p>Your inbox is getting full, would you like us to enable
				automatic archiving of old messages?</p>
		</div> -->
		<div class="actions">
			<div class="ui green basic cancel inverted button">
				<i class="checkmark icon"></i> OK
			</div>
		</div>
	    </div>
	</div>
</body>
</html>