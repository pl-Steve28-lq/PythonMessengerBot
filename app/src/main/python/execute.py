def getResponse(code, cls, activity):
    ns_name = '_namespace_wow'
    exec(code+f'\n{ns_name}=locals()')
    ns = eval(ns_name)
    del ns[ns_name]
    clz = eval(cls)
    try: c = clz(activity)
    except: c = clz()
    f = getattr(c, 'response', lambda *args:1)
    for i in ns: f.__globals__[i] = ns[i]
    return f

def emptyFunction(*args): return