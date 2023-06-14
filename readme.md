<h2>Система управления проектами </h2>

* Описание :

![d](https://github.com/turaldinio/ProjectManagementService/assets/65041919/2e616d83-7991-44d4-8dcd-b97af83dd3d6)

Приложение состоит из 5 основных модулей:

* app - содержит в себе конфигурационные файлы

* business -данный модуль представляет из себя бизнес логику приложения,
  и содержит в себе 3 дополнительных модуля:
*
    * model - представляет из себя модели сущностей
*
    * repository -отвечает за получение/изменение/сохранение/удаление данных из хранилища
*
    * service -модуль, содержащий в себе основную бизнес логику приложения

* dto - содержит сущности, возвращаемые клиенту
* web - модуль, содержащий контроллеры
* common -модуль, содержащий вспомогательные данные

Основные сущности: 

* Employee:сущность сотрудника.

*
    * Фамилия (обязательное поле)
*
    * Имя (обязательное поле)
*
    * Отчество (не обязательное)
*
    * Должность (не обязательное)
*
    * Учетная запись (не обязательное), но уникальное значение среди активных сотрудников (у двух активных профилей не
      может быть одинаковой учетной записи, если оно не пустое).
*
    * Адрес электронной почты (не обязательное)
*
    * Статус сотрудника - (обязательное поле) фиксированный набор значений (Активный, Удаленный).

* Основные операции над проектами
    *
        * Создание профиля сотрудника. При создании сотрудника должна создаваться карточка сотрудника с перечисленным
          выше набором атрибутов, также статус сотрудника становится Активный.
    *
        * Изменение сотрудника. При редактировании сотрудника должны редактировать поля профиля сотрудника. Удаленного
          сотрудника изменить нельзя.
    *
        * Удаление сотрудника. При удалении сотрудника, сотрудник переводится в статус Удаленный.
    *
        * Поиск сотрудников. Поиск осуществляется по текстовому значению, которое проверяется по атрибутам Фамилия, Имя,
          Отчество, учетной записи, адресу электронной почты и только среди активных сотрудников.
    *
        * Получение карточки сотрудника либо по идентификатору профиля, либо по УЗ с

* Проект сущность, состоящая из:
*
    * Код проекта - некоторое уникальное имя проекта. Является обязательным и уникальным среди всех проектов.
*
    * Наименование - текстовое значение содержащее короткое наименование проекта.. Обязательное поле.
*
    * Описание - текстовое значение содержащее более детальную информацию о проекте. Не обязательное поле.
*
    * Статус проекта - текстовое значение, обозначающее состояние проекта. Список статусов фиксированный (Черновик, В
      разработке, В тестировании, Завершен). Обязательное поле.
* Основные операции над проектами:
*
    * Создание проекта. При создании проекта должны быть заполнены все обязательные поля, и выполнена проверка на
      уникальность кода проекта. Проект создается в статусе Черновик.
*
    * Изменение проекта. При изменении проекта должны изменяться поля карточки проекта.
*
    * Поиск проектов. Поиск должен осуществляться по текстовому значению (по полям Наименование проекта, Код проекта)  и
      с применением фильтров по Статусу проекта. Т.е. на вход передается некоторое текстовое значение и список статусов.
*
    * Перевод в другое состояние проекта. Можно перевести в другой статус проект, согласно диаграмме ниже о возможных
      изменениях статуса.

*
    * Черновик -> В разработке -> В тестировании -> Завершен


* Команда проекта - группа сотрудников объединенных одним проектом

* * В разных командах один сотрудник может принимать различные роли, но только одну роль внутри проекта.

* * Итого команда проекта состоит из списка связей: проект - сотрудник - роль в проекте.

* Основные операции:
*
    * Добавить участника проекта. Т.е. добавить сотрудника в команду проекта с определенной ролью.
*
    * Удалить участника проекта. Т.е. исключить из команды по проекту сотрудника.
*
    * Получить всех участников проекта.


* Задача

* * Задача назначает на сотрудника внутри проекта. Структура задач линейная (никаких деревьев и т.п.).

*
    * Задача обладает следующим набором атрибутов:

*
    * Наименование задачи - текстовое значение, отражающее краткую информацию о задачи (обязательное поле).
*
    * Описание задачи - текстовое значение, содержащее детальное описание задачи. (не обязательное поле)
*
    * Исполнитель задачи - сотрудник, которому необходимо исполнить задачу. (не обязательное поле). Можно выбрать
      исполнителя только участника проекта ( сотрудник добавленный в команду проекта). Назначить исполнителя можно
      только сотрудника в статусе Активный.
*
    * Трудозатраты - оценка, сколько в часах необходимо на ее исполнение. (обязательное поле)
*
    * Крайний срок - дата, когда задача должна быть исполнена. Нельзя выбрать дату если дата меньше, чем дата создания +
      трудозатраты. Обязательное поле.
*
    * Статус задачи - фиксированный список состояний задачи (Новая, В работе, Выполнена, Закрыта).
*
    * Автор задачи - заполняется автоматически, тем кто создавал задачу. Автором задачи может являться только участник
      проекта.
*
    * Дата создания - дата когда задача была создана.
*
    * Дата последнего изменения задачи - дата последнего редактирования задачи (но не изменение статуса задачи).

*
    * Новая -> В разработке -> Выполнена -> Закрыта

* В системе предусмотрена аутентификация по токенам. Для получения токена необходимо направить
  GET запрос на /auth/login и передать данные для авторизации:

{

"login":"login,

"password":"password

}

Токен проверяется перед каждым запросом содержащий в себе /private url

Отношение между сущностями:

![d](https://github.com/turaldinio/ProjectManagementService/assets/65041919/2e616d83-7991-44d4-8dcd-b97af83dd3d6)



* Загрузка файлов
* * Для сущностей задача и проект, есть возможность загружать файлы. Файлы могут иметь любое разрешение.
* * Не допускается наличие двух одинаковых файлов для одной сущности. Файлы сущности скачиваются в формате zip

* Sl4j
*
    * Каждый сервис ведет логирование своих действий в файл : ProjectManagementSystem/logs/SERVICE_NAME.log
      Также действия всех логеров записывается в общий файл :   ProjectManagementSystem/logs/allServices.log

* RabbitMq

*
    * При назначении на сотрудника задачи, при наличии у сотрудника почты,ему направляется уведомление о назначенной
      задачи.
*
    * Для проверки работоспособности уведомления, можно подменить почту сотрудника на свою .Для этого необходимо в
      pm-service -> resources -> service.properties указать mail.mock почту, на которую необходимо направить уведомление

* * Для отправки уведомлений со своей почты, замените
spring.mail.username(ваша почта) и
spring.mail.password(пароль, сгенерированный google двухфакторной аутентификаций)