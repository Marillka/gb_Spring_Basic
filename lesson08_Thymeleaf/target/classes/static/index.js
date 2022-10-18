// создаем приложение, которое называется "app". Квадратные скобки - означают, что создается новое приложение без каких либо дополнительных зависимостей.
// Далее для этого приложения создаем контроллер "indexController". И внутри него его локика.
// В функции добавляются две стандарные зависимости $scope и $http.
// $scope - некий контекст, куда вы можете складывать данные и обмениваться этими данными с фронтом. То есть если вы здесь положили что то в $scope (например $scope.a = 10), то ее можно будет использовать в .html файле.
// Также если в html вызываются какие то функии, то эти функции должны быть объявлены в .js.
// $http - Если вам нужно запрашивать с каких то бекендов, сервисов, то вам не обойтись без чего то, что позволит посылать http запросы.
// Предназначен, чтбы посылать POST, GET запросы.
//
angular.module('app', []).controller('indexController', function ($scope, $http) {
    // константа для удобства - чтобы знать корень приложения
    const contextPath = 'http://localhost:8189/app';

    //console.log(123);


    // запихиваем функции в $scope
    $scope.loadStudents = function () {
        // посылваем get запрос по адресу
        $http.get(contextPath + '/students')
            // запрос отправили. Когда придет ответ (callback) то выполняем код дальше.
            // дальше в функцию засовываем response от запроса и работаем с ней.
            .then(function (response) {
                // console.log(response.data);
                // соответственно, когда в $scope изменяется StudentList - то он принудительно перересовывыается в html файле
                $scope.StudentsList = response.data;
            });
    };

    $scope.deleteStudent = function (studentId) {
        $http.get(contextPath + '/students/delete/' + studentId)
            .then(function (response) {
                $scope.loadStudents();
            });
    }

    // тут получается запрос:
    // GET 'http://localhost:8189/app/students/score/change_score?studentId=1&delta=50'
    // POST 'http://localhost:8189/app/students/score/change_score'
    // В GET запросе параметры загоняются в Url. А в POST - в тело запроса.
    $scope.changeScore = function (studentId, delta) {
        // console.log('Click!');
        $http({
            url: contextPath + '/students/score/change_score',
            method: 'GET',
            params: {
                studentId: studentId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadStudents();
        });
    }

    $scope.createStudentJson = function () {
        console.log($scope.newStudentJson);
        // после запятой указываем, то что должно подтянутья в тело запроса.
        $http.post(contextPath + '/students', $scope.newStudentJson)
            .then(function (response) {
                $scope.loadStudents();
            });
    }

    $scope.sumTwoNumbers = function () {
        console.log($scope.calcAdd);
        $http({
            url: contextPath + '/calc/add',
            method: 'post',
            params: {
                a: $scope.calcAdd.a,
                b: $scope.calcAdd.b
            }
        }).then(function (response) {
            alert('Сумма равна ' + response.data.value);
            // делаем null - чтобы затерлись параметры ввода в форме
            $scope.calcAdd = null;
        });
    }


    // при старте вызываем функцию для загрузки студентов.
    $scope.loadStudents();
});