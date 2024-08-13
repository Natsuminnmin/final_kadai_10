## 概要
動物の管理システムとして「動物種、名前、誕生日、体重」の登録とREAD処理を行えるAPIをまず実装しました。

## 動作確認
### READ機能について
"pet/{id}"で登録した動物の情報（「ID、動物種、名前、誕生日、体重」）を呼び出せるようにしました。

例：id:1と検索した時の場合
![kadai10-GET](https://github.com/user-attachments/assets/c41d509f-5ce6-4cc5-9709-260153c90946)


また、登録されていないIDが検索された時、エラーコード：404でエラーメッセージ「That ID：{id} not registered.（このID：{id}は登録されていません）」がレスポンスボディに表示されるようにしました。

例：id:15と検索した時の場合
![kadai10-GET_Error](https://github.com/user-attachments/assets/f6cc0fed-2b2a-466e-84cb-5b6b6aed6612)


### CREATE機能について
"pet/"でリクエストボディに「動物種、名前、誕生日、体重」を入力すると「{name}-chan's registration has been completed!（{name}ちゃんの登録が完了しました！)」登録されたという文言が表示されるようにしました。
また、レスポンスのヘッダーに登録した内容のURLを表示できるようにし、ステータスも201：CREATEと表示されるようにしました。

例：「動物種、名前、誕生日、体重」＝「dog、シロ、2024-01-03、2.23」とリクエストボディに記載した場合
![kadai10-POST_Body](https://github.com/user-attachments/assets/98ca3634-a702-47aa-9347-80b8a3082e52)
![kadai10-POST_Header](https://github.com/user-attachments/assets/d1569b64-d727-4435-9faf-04877a08e312)



## 次回の実装について
・POSTでリクエストボディに不備があった際にエラーコード404で、エラーメッセージも設定されたのが表示されるように実装を進めること
