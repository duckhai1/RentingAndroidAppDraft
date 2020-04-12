#!/usr/bin/env python3

# TODO: refactor

import pathlib
import os

MODEL_ROOT = pathlib.Path('book2play_model/')
TESTS_DIR = MODEL_ROOT / 'tests'
PROCEDURES_DIR = MODEL_ROOT / 'stored_procedures'
SCHEMAS_DIR = MODEL_ROOT / 'schemas'

BUILD_OUT = pathlib.Path('./build')
SETUP_OUT = BUILD_OUT / 'setup.sql'
SETUP_TEST_OUT = BUILD_OUT / 'setup_test.sql'
TESTS_OUT = BUILD_OUT / 'tests.sql'

INIT = MODEL_ROOT / 'init.sql'
INIT_TEST = MODEL_ROOT / 'init_test.sql'


def create_setup_script(out_file, init_file, schemas_dir, stored_procedures_dir):
    with open(out_file, "w") as fo:
        print("WRITING %s" % out_file)
        with init_file.open('r') as fi:
            print('READING INIT:', init_file)
            content = fi.read()
            fo.write(content)
            fo.write('\n\n')

        for x in sorted(schemas_dir.glob("*.sql")):
            if x != init_file and not x.is_dir():
                with x.open('r') as fi:
                    print('READING SETUP SCRIPT:', x)
                    content = fi.read()
                    fo.write(content)
                    fo.write('\n\n')

        for x in stored_procedures_dir.glob("*.sql"):
            with x.open('r') as fi:
                print('READING PROCEDURE:', x)
                content = fi.read()
                fo.write(content)
                fo.write('\n\n')


def create_tests(out_file, tests_dir):
    with open(out_file, "w") as fo:
        print("WRITING %s" % out_file)
        for x in tests_dir.glob("*.sql"):
            with x.open('r') as fi:
                print('READING TEST CASE:', x)
                content = fi.read()
                fo.write(content)
                fo.write('\n\n')


def main():
    if not os.path.exists(BUILD_OUT):
        os.makedirs(BUILD_OUT)

    create_setup_script(SETUP_OUT, INIT, SCHEMAS_DIR, PROCEDURES_DIR)
    create_setup_script(SETUP_TEST_OUT, INIT_TEST, SCHEMAS_DIR, PROCEDURES_DIR)
    create_tests(TESTS_OUT, TESTS_DIR)


if __name__ == '__main__':
    main()
