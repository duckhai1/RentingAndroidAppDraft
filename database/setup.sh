#!/bin/bash
./setup.py
 /usr/local/opt/mysql@5.7/bin/mysql -uroot -p < ./build/setup.sql