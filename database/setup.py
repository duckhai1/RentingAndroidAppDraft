#!/usr/bin/env python3

# TODO: refactor

import pathlib
import os

DEFAULT_ROOT = './'
DEFAULT_MODEL_ROOT = 'book2play_model/'

DEFAULT_BUILD_OUT = './build'
DEFAULT_OUT = DEFAULT_BUILD_OUT + '/setup.sql'
TESTS_OUT = DEFAULT_BUILD_OUT + '/tests.sql'

db_root = pathlib.Path(DEFAULT_ROOT)
model_root = pathlib.Path(DEFAULT_MODEL_ROOT)
init_file = model_root / 'init.sql'
stored_procedures = model_root / 'stored_procedures'
tests = model_root / 'tests'

if not os.path.exists(DEFAULT_BUILD_OUT):
    os.makedirs(DEFAULT_BUILD_OUT)

with open(DEFAULT_OUT, "w") as fo:
    print("WRITING %s" % DEFAULT_OUT)
    with init_file.open('r') as fi:
        print('READING INIT:', init_file)
        content = fi.read()
        fo.write(content)
        fo.write('\n\n')

    for x in sorted(model_root.glob("*.sql")):
        if x != init_file and not x.is_dir():
            with x.open('r') as fi:
                print('READING SETUP SCRIPT:', x)
                content = fi.read()
                fo.write(content)
                fo.write('\n\n')

    for x in stored_procedures.glob("*.sql"):
        with x.open('r') as fi:
            print('READING PROCEDURE:', x)
            content = fi.read()
            fo.write(content)
            fo.write('\n\n')

print("WRITING %s" % TESTS_OUT)
with open(TESTS_OUT, "w") as fo:
    for x in tests.glob("*.sql"):
        with x.open('r') as fi:
            print('READING TEST CASE:', x)
            content = fi.read()
            fo.write(content)
            fo.write('\n\n')
