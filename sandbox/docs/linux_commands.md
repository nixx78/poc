##_Операции с файлами/директориями_

### ls список файлов/директорий

Пример расширенного вывода

$ ls -lh --block-size K --time-style=full-iso
**ls --color -lh --block-size K --time-style=full-iso**
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ ls --color -lh --block-size K --time-style=full-iso
total 3130K
-rw-r--r-- 1 nixx7 197609 3125K 2022-11-21 09:43:03.527068700 +0200 2.txt
-rw-r--r-- 1 nixx7 197609    1K 2022-11-21 10:11:14.187940700 +0200 777.txt
-rw-r--r-- 1 nixx7 197609    1K 2022-11-21 09:40:07.192959600 +0200 new.1.txt
drwxr-xr-x 1 nixx7 197609    0K 2022-11-21 10:15:48.574774700 +0200 sample.dir
```
Вывод файлов с сортировкой: 
**ls -lh --time-style=full-iso --sort=time -r**
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ ls -lh --time-style=full-iso --sort=time -r
total 3.1M
-rw-r--r-- 1 nixx7 197609   40 2022-11-21 09:40:07.192959600 +0200 new.1.txt
-rw-r--r-- 1 nixx7 197609 3.1M 2022-11-21 09:43:03.527068700 +0200 2.txt
-rw-r--r-- 1 nixx7 197609   18 2022-11-21 10:11:14.187940700 +0200 777.txt
drwxr-xr-x 1 nixx7 197609    0 2022-11-21 10:15:48.574774700 +0200 sample.dir
-rw-r--r-- 1 nixx7 197609   44 2022-11-21 10:18:18.131927900 +0200 app.log
```

### cd переход в директорию

### pwd показ текущей директории

### mkdir создание директории

### mv перемещение или переименование файла

### cp копирование файла

### rm удаление файла или директории
* Удаление директории: rm -r <DIR_NAME>

### ln создание ссылки на файл


##_Операции с содержимым файлов_

### touch создание файла 


