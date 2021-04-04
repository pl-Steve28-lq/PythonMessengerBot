def getResponse(code, cls, activity, onError):
    ns_name = '_namespace_wow_Steve28'
    exec(code+f'\n{ns_name}=locals()')
    ns = eval(ns_name)
    del ns[ns_name]
    clz = eval(cls)
    try: c = clz(activity)
    except: c = clz()
    f = getattr(c, 'response', lambda *args:1)
    for i in ns: f.__globals__[i] = ns[i]
    return Pythonize(f, onError)

def Pythonize(func, onError):
    def f(sender, msg, room, isGroupChat, replier, profileImage, packageName):
        try: func(str(sender), str(msg), str(room), bool(isGroupChat), replier, profileImage, str(packageName))
        except Exception as ex: onError(str(ex))
    return f

def emptyFunction(*args): return