##_Операции с файлами/директориями_##

### Список файлов/директорий: ls ###

Пример расширенного вывода (размер файла, timestamp с секундами)

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
Вывод файлов с сортировкой по времени в обратном порядке (-r): 
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

### Переход в директорию: cd ### 
Переход в корневую директорию пользователя: $ cd~  
Переход в корневую директорию диска: cd /c


### Показ текущей директории: pwd ### 

### Создание директории: mkdir ###
```
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ mkdir -v new_dir1 new_dir2
mkdir: created directory 'new_dir1'
mkdir: created directory 'new_dir2'
```
### Перемещение или переименование файла: mv ### 
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ mv -v 2.txt 2_new.txt
renamed '2.txt' -> '2_new.txt'
````
### Копирование файла: cp ### 

### Удаление файла или директории: rm  ###
Удаление директории: rm -r <DIR_NAME>

### Создание ссылки на файл: ln  ###

## _Работа с архивами_ ##

### Создание архива ###
tar -czvf txt.gz *.txt
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ tar -czvf txt.gz *.txt
2.txt
777.txt
new.1.txt
````
### Получение содержимого архива ###
tar -ztvf txt.gz
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ tar -ztvf txt.gz
-rw-r--r-- nixx7/197609 3199983 2022-11-21 09:43 2.txt
-rw-r--r-- nixx7/197609      18 2022-11-21 10:11 777.txt
-rw-r--r-- nixx7/197609      40 2022-11-21 09:40 new.1.txt
````
### Распаковка архива ###
Распаковка архива в директорию logs (директория уже должны существовать):
tar -xvf txt.gz -C ./logs 
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ tar -xvf txt.gz -C ./logs
2.txt
777.txt
new.1.txt
````
##Операции с содержимым файлов ###
### Создание файла touch ####

#### Получение содержимого файла: cat ####
Получить все строки с ошибками из файла, записать в отдельный файл и показать его содержимое
````
nixx7@LAPTOP-M44LHQT2 MINGW64 /c/tmp/linux_playground
$ cat -n app.log | grep "ERROR" > app.error | cat app.error
16  ERROR Line 1
18  ERROR Line 2
````

#### Потоковый редактор текста: sed ####
Sed (StreamEDitor)  
sed -n 5p app.log - получение строки по номеру  


#### Редактирование файла: vi ####

#### Получение содержимого файла: tail ####
_tail -n 3 app.log_ - получение последних 3 строк из файла  
_tail -n +5 app.log_ - получение последних строчек из файла, начиная с 5 строки  
_tail -n 5 app.log_ | grep -n "ERROR" - получение строк с ошибкой из последних 5 строк, в результат включается номер строки  




 


