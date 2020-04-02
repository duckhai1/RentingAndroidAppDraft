#!/usr/bin/env python3
import pathlib

DEFAULT_ROOT = './'
DEFAULT_MODEL_ROOT = 'book2play_model/'
DEFAULT_OUT = './setup.sql' 

db_root = pathlib.Path(DEFAULT_ROOT)
model_root = pathlib.Path(DEFAULT_MODEL_ROOT)
init_file = model_root / 'init.sql'
stored_procedures = model_root / 'stored_procedures'

with open(DEFAULT_OUT, "w") as fo:
    with init_file.open('r') as fi:
        fo.write(fi.read())
        fo.write('\n')

    for x in model_root.iterdir():
        if x != init_file and x != stored_procedures:
            with x.open('r') as fi:
                fo.write(fi.read())
                fo.write('\n')
                
    for x in stored_procedures.iterdir():
        with x.open('r') as fi:
            fo.write(fi.read())
            fo.write('\n')