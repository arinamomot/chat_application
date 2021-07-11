# API

## Methods

_login_

Required:

| field  | type  | description  | example  |   |
|---|---|---|---|---|
| action  |  string | action type  | login   |   |
| data.mail  | String  | mail  | taet@tef.von  |   |
|  data.password | String  | pasword  | erlkgnlewrg  |   |

REQUEST:
```json
{
  "action": "login",
  "data": {
    "mail": "mail@mIL.OM",
    "password": "pass"  
  }
}
```
RESPONSE:
```json
{
  "action": "login",
  "data": {
    "token": "OINEFOIEFOI)(J#)(JT)(J"
 }
}
```

_signup_

Required:

| field  | type  | description  | example  |   |
|---|---|---|---|---|
| action  |  String | action type  | signup   |   |
| data.firstname  |  String | firstname  | Arina   |   |
| data.lastname  |  String | lastname  | Momot   |   |
| data.mail  | String  | mail  | taet@tef.von  |   |
|  data.password | String  | pasword  | erlkgnlewrg  |   |
|  data.country | String  | country  | RU  |   |
|  data.birthday | String  | birthday  | 06/10/2000  |   |
|  data.gender | String  | gender  | f  |   |

REQUEST:
```json
{
  "action": "signup",
  "data": {
    "firstname" : "Arina",
    "lastname" : "Momot",
    "mail": "mail@mIL.OM",
    "password": "pass",  
    "country" : "RU",
    "birthday" : "06/10/2000",
    "gender" : "f"
  }
}
```
RESPONSE:
```json
{
  "action": "signup",
  "error": "Error: mail is taken",
  "data": {
    "token": "OINEFOIEFOI)(J#)(JT)(J"
 }
}
```

_sendMessage_ - requires token

Required:

| field  | type  | description  | example  |   |
|---|---|---|---|---|
| action  |  string | action type  | sendMessage   |   |
| data.text  | String  | text    | hello world  |   |
|  data.recipient | integer  | id of group  | 6983456  |   |

Request:
```json
{
  "action": "sendMessage",
  "token": "823982739857298375lweknflkwefn",   
  "data": {
    "text": "hello",
    "recipent": 28736
  }
}
```
RESPONSE

```json
{
    "action": "sendMessage",
    "error":"Error: wrong token",
    "data": {
      "timeSend": "1348958734",
      "recipient": 129783, 
      "message_id": 100
    }
}
```

_receiveMessages_ - token

Required:

| field  | type  | description  | example  |   |
|---|---|---|---|---|
| action  |  string | action type  | receiveMessages   |   |

Request:
```json
{
  "action": "receiveMessage",
  "token": "823982739857298375lweknflkwefn"
}
```
RESPONSE

```json
{
    "action": "receiveMessage",
    "data": {
      "messages": [{
        "from": 8923,
        "recipient": 92034,
        "text": "test",
        "smile": ":fun:"
        },{},{}]
    }
    
}
```

_createGroup_

Required:

| field  | type  | description  | example  |   |
|---|---|---|---|---|
| action  |  string | action type  | createGroup   |   |
| data.title | String  | title of group  | super group  |   |
| data.description | String  | description of group  | This group is super  |   |

Request:
```json
{
  "action": "creatGroup",
  "data": {
    "description" : "cvut fel",
    "title" : "super group"
  }
}
```
RESPONSE

```json
{
    "action": "createGroup",
    "data": {
      "title": "super group",
      "date": "09/04/2020",
      "countOfMembers": 1
}
}
```

_getMembersOfGroup_

REQUEST:

// todo

_findUser_

Required:

| field  | type  | description  | example  |   |
|---|---|---|---|---|
| action  |  string | action type  | findUser   |   |
| data.firstname  |  String | firstname  | Arina   |   |
| data.lastname  |  String | lastname  | Momot   |   |

Request:
```json
{
  "action": "findUser",
  "data": {
    "firstname" : "Arina",
    "lastname" : "Momot"
  }
}
```
RESPONSE

```json
{
    "action": "findUser",
    "data": {
      "users": [{"id":  199}] 
    }
}
```

_addUserToMyContactList_

_SendSmile_

