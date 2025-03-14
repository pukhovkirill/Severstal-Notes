## Тестовое задание "Заметки"

## Стек

- Java 17
- Maven
- Backend
    - Spring MVC
    - Spring Security 6, Spring Session
    - Thymeleaf
- Базы данных
    - Postgresql
    - MinIO
    - Spring Data
- Frontend - HTML/CSS, Bootstrap

## База данных

В качестве базы данных используется Postgresql

### Таблица `users`

| Колонка  | Тип      | Комментарий                                      |
|----------|----------|--------------------------------------------------|
| id       | serial   | Айди пользователя, автоинкремент, первичный ключ |
| name     | varchar  | Имя пользователя                                 |
| email    | varchar  | Email пользователя                               |
| password | varchar  | Пароль пользователя                              |

### Таблица `notes`

| Колонка   | Тип       | Комментарий                                 |
|-----------|-----------|---------------------------------------------|
| id        | serial    | Айди заметки, автоинкремент, первичный ключ |
| title     | varchar   | Название                                    |
| content   | text      | Контент заметки                             |
| create_at | timestamp | Дата создания заметки                       |
| owner_id  | bigint    | Внешний ключ на владельца заметки           |

### Деплой

```shell
$ git clone https://github.com/pukhovkirill/Severstal-Notes
$ cd Severstal-Notes
$ chmod +x generate_env.sh && ./generate_env.sh
$ sudo docker-compose up
```

Лицензия
=====================
Copyright (c) 2025 Kirill Pukhov\
Распространяется по лицензии MIT License. Подробнее см. файл LICENSE.
