angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    // $scope.loadStudents = function () {
    //     $http.get(contextPath + '/students')
    //         .then(function (response) {
    //             $scope.StudentsList = response.data;
    //         });
    // };

    $scope.loadStudents = function (pageIndex = 1) {
        $http({
            url: contextPath + '/students',
            method: 'GET',
            params: {
                // если name_part задан - то его и отправляем, иначе null
                name_part: $scope.filter ? $scope.filter.name_part : null,
                min_score: $scope.filter ? $scope.filter.min_score : null,
                max_score: $scope.filter ? $scope.filter.max_score : null
            }
        }).then(function (response) {
            $scope.StudentsList = response.data.content;
        });
    };

    $scope.deleteStudent = function (studentId) {
        $http.delete(contextPath + '/students/' + studentId)
            .then(function (response) {
                $scope.loadStudents();
            });
    }

    $scope.createStudentJson = function () {
        console.log($scope.newStudentJson);
        $http.post(contextPath + '/students', $scope.newStudentJson)
            .then(function (response) {
                $scope.loadStudents();
            });
    }

    $scope.loadStudents();
});