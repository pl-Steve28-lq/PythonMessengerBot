def getResponse(code, cls, activity, onError):
    ns_name = '_namespace_wow_Steve28'
    exec(code+f'\n{ns_name}=locals()')
    ns = eval(ns_name)
    del ns[ns_name]
    cls = eval(cls)
    init = globalize(cls.__init__, ns)
    obj = globalize(lambda x: cls.__new__(x), ns)(cls)
    try: init(obj, activity)
    except Exception as ex: init(obj)
    f = getattr(obj, 'response', emptyFunction)
    return Pythonize(globalize(f, ns), onError)

def Pythonize(func, onError):
    def f(sender, msg, room, isGroupChat, replier, profileImage, packageName):
        try: func(str(sender), str(msg), str(room), bool(isGroupChat), replier, profileImage, str(packageName))
        except Exception as ex: onError(str(ex))
    return f

def globalize(func, globals):
    for i in globals: func.__globals__[i] = globals[i]
    return func

def emptyFunction(*args): return