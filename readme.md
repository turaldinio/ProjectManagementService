<h2>Система управления проектами </h2>


![Диаграмма без названия drawio](https://github.com/turaldinio/ProjectManagementService/assets/65041919/239f1c0f-1fa5-4bea-a896-97020da0aa91)




Приложение состоит из 5 основных модулей:
* app - содержит в себе конфигурационные файлы

* business -данный модуль представляет из себя бизнес логику приложения,
и содержит в себе 3 дополнительных модуля:
* * model - представляет из себя модели сущностей 
* * repository -отвечает за получение/изменение/сохранение/удаление данных из хранилища
* * service -модуль, содержащий в себе основную бизнес логику приложения 

* dto - содержит сущности, возвращаемые клиенту
* web - модуль, содержащий контроллеры
* common -модуль, содержащий вспомогательные данные

В приложение добавлены методы, для создания/удаления/нахождение сущности/ей Employee.

Все сущности хранятся в формате json.
* create метод отвечает за создание сущности и записи ее в хранилище (repository/src/main/resources/data.txt).
Если по указанному пути файла data.txt нет, он будет создан автоматически.
На вход принимает сущность CreteEmployeeDto, после чего, при помощи маппера, получается сущность employee
которая и будет записана в бд. Пользователю возвращается сущность EmployeeDto
* update метод, отвечающий за обновление данных. Принимает на вход id пользователя, которого необходимо обновить
, и новые данные Employee. После обновления полей сущности, перезаписывает данных в бд.
* getById метод для получения сущности по ее id. В случае, если пользователя с указанным id нет,
будет выброшено исключение Exception
* getAll возвращает всех пользователей из бд
* deleteById метод, для удаление пользователя по id. Если пользователь с указанным id
не обнаружен, выбросить Exception исключение

<h3>17.05.23 Добавлена возможность crud действий над сущностью Employee в бд (jdbc)</h3>
* Добавлен новый метод в EmplyeeService (searchWithFilter),который, принимает на вход объект FilterEmployee, на основании которго, выполняет
фильтрацию из списка всех сотрудников.

* Разработана схема бд:

![team](https://github.com/turaldinio/ProjectManagementService/assets/65041919/b3471977-f2ef-4e90-bd4a-19ed4fa1d3d8)


