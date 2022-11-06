angular.module('app2', []).controller('usersController', function ($scope, $http) {

    // const contextPath = 'http://localhost:8189/homework/api/v1';
    const contextPath = 'http://localhost:8189/homework';

    $scope.loadUsers = function () {
        $http.get(contextPath + '/users')
            .then(function (response) {
                console.log(response)
                $scope.UsersList = response.data;
            });
    };




    //
    // $scope.loadProducts = function (pageIndex = 1) {
    //     $http({
    //         url: contextPath + '/products',
    //         method: 'GET',
    //         params: {
    //             title_part: $scope.filter ? $scope.filter.title_part : null,
    //             min_cost: $scope.filter ? $scope.filter.min_cost : null,
    //             max_cost: $scope.filter ? $scope.filter.max_cost : null
    //         }
    //     }).then(function (response) {
    //         console.log(response)
    //         $scope.ProductsList = response.data;
    //     });
    // }
    //
    // $scope.loadUsers = function () {
    //     $http({
    //         url: contextPath + '/users',
    //         method: 'GET',
    //     }).then(function (response) {
    //         console.log(response)
    //         $scope.UsersList = response.data;
    //     });
    // }



/*
    $scope.createProduct = function () {
        console.log($scope.newProduct);
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.deleteProduct = function (productId) {
        $http.get(contextPath + '/products/delete/' + productId)
            .then(function (response) {
                console.log(response)
                $scope.loadProducts();
            });
    }

    $scope.loadCart = function () {
        $http.get(contextPath + '/cart')
            .then(function (response) {
                console.log(response)
                $scope.CartList = response.data;
            });
    }

    $scope.deleteProductFromCart = function (productId) {
        $http.get(contextPath + '/cart/delete/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.addToCart = function (productId) {``
        $http({
            url: contextPath + '/cart',
            method: 'POST',
            params: {
                productId: productId
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    }*/



    $scope.loadUsers();

});
