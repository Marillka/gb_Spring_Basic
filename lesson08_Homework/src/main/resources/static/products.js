angular.module('app', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';


    $scope.loadProducts = function () {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

    $scope.deleteProduct = function (productId) {
        $http.get(contextPath + '/products/delete/' + productId)
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.changeCost = function (productId, delta) {
        $http({
            url: contextPath + '/products/cost/change_cost',
            method: 'GET',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.createProductForm = function () {
        console.log($scope.newProduct);
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.loadProducts();
                $scope.newProduct.name = '0';
                $scope.newProduct.cost = '0';
            });
    }

    $scope.findProductsBetween = function () {
        console.log($scope.productCost);
        $http({
            url: contextPath + '/products/cost_between',
            method: 'post',
            params: {
                min: $scope.productCost.min,
                max: $scope.productCost.max
            }
        }).then(function (response) {
                console.log(response.data)
                $scope.ProductsList = response.data;
            });
    }

    $scope.loadProducts();
});