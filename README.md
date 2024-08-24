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

また、クエストボディに不備があった際（空欄や空文字など）にValidationを利用し、エラーコード400で返す実装を行いました。

例：すべての項目をNULLで登録した場合
![kadai10-validation](https://github.com/user-attachments/assets/930c0b15-6b8e-4836-9179-f543c77c8506)
![kadai10-validation-2](https://github.com/user-attachments/assets/a47a49cf-c06e-4135-b498-3b9fb7f11276)
上記のように、エラーメッセージが表示され、レスポンスボディに登録されたURLの表示もありません。

また、動物種/名前は50字以内、誕生日は過去か現在の日付、体重は整数3桁、小数点以下は2桁で登録しなければエラーになるようにもしました。
例：動物種/名前：50文字以上、誕生日：未来日、体重：整数4桁、小数点以下3桁で登録した場合
![kadai10-validation-3](https://github.com/user-attachments/assets/2cc0aab9-8142-4314-b083-7aa146028bb1)
![kadai10-validation-4](https://github.com/user-attachments/assets/de708f0f-ab46-41ad-a91d-71cf00577f39)
こちらも同様にエラーメッセージが表示され、レスポンスボディに登録されたURLの表示はありません。

## 次回の実装について
UPDATEの実装を進めます
