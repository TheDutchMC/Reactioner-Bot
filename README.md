# Reactioner Bot
A bot to help you in role managment. Reactioner will give a user a role when they react with an emoji to a specific message.

## Installation
### Docker-Compose
```yaml
version: '3'
service:
    reactioner:
        container_name: reactioner
        image: docker-registry.k8s.array21.dev/reactioner-bot:latest
        volume:
        - './config:/config/config.yml'
        environment:
        - 'BOT_TOKEN=YOUR_BOT_TOKEN'
```

### Kubernetes
This is how I do it, there're probably a million other ways to do it though.
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: reactioner-bot
data:
  config: |
    botToken: FROM_ENV
    commandPrefix: '%'
    messageEntries:
    - messageId: some_message_id
      reactionEntries:
      - reactionName: some_emoji_name
        roleId: some_role_id
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: reactioner-bot
  labels:
    app: reactioner-bot
spec:
  replicas: 1
  selector:
    matchLabels:
      app: reactioner-bot
  template:
    metadata:
      labels:
        app: reactioner-bot
    spec:
      containers:
      - name: reactioner-bot
        image: docker-registry.k8s.array21.dev/reactioner-bot:latest
        env:
        - name: "BOT_TOKEN"
          valueFrom:
            secretKeyRef:
              name: reactioner-bot
              key: BOT_TOKEN
        volumeMounts:
        - name: config
          mountPath: /config/
      volumes:
      - name: config
        configMap:
          name: reactioner-bot
          items:
          - key: config
            path: config.yml
```
>Note: You need to create a secret called `reactioner-bot` with a key inside it `BOT_TOKEN` that contains your bot token.

## Config
Example:
```yaml
    botToken: FROM_ENV
    commandPrefix: '%'
    messageEntries:
    - messageId: '740688169078358097'
      reactionEntries:
      - reactionName: purple_heart
        roleId: 738026804815724684
      - reactionName: blue_heart
        roleId: 738026729549070426
      - reactionName: yellow_heart
        roleId: 738026881697185883
      - reactionName: green_heart
        roleId: 738026938236403852
```
>Note: To make deployments on K8s easier, you can use `FROM_ENV` in the `botToken` field, Reactioner will then use the environmental variable `BOT_TOKEN`. Though you can also just fill in the token directly.

## Licence
This project is licenced under the [GPL-v3 Licence](LICENCE). Contributors will retain the copyright on their contributions.

## Contributing
Any contributions are welcome. Just open a PR or Issue and go :)