# setup for py2exe by Nancy Schorr
import sys

from distutils.core import setup
import py2exe

                                            #data_files=[]
setup ( 
       console = ['nfitoaaf.py']
                                               #options = {'includes':['sys','glob', 'os', 'datetime']}
                  )