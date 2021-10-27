Profiles:
Output:
1) local - if local storage is need to be used as decomposition output.
2) rest - if decomposition output must be sent as rest request.
3) rmq - if decomposition output must be sent with RabbitMQ.

Properties:
service.decomposition.sensitivity - color sensitivity during adjacent pixels search. Adjacent pixel will be ignored if
its standard deviation of initial pixel will be greater than this value.
service.decomposition.min-density - minimal density of found subimage. Subimage will be skipped if its width or height 
is less than this value. 
service.output.local.path - path to local storage used to save decomposition result.
service.output.image-type - type of images to work with.