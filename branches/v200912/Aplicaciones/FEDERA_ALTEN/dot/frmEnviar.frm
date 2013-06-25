VERSION 5.00
Begin {C62A69F0-16DC-11CE-9E98-00AA00574A4F} frmEnviar 
   Caption         =   "Enviar por eMail"
   ClientHeight    =   2100
   ClientLeft      =   45
   ClientTop       =   435
   ClientWidth     =   8070
   OleObjectBlob   =   "frmEnviar.frx":0000
   ShowModal       =   0   'False
   StartUpPosition =   1  'Centrar en propietario
End
Attribute VB_Name = "frmEnviar"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Sub SendDocumentInMail()

Dim bStarted As Boolean
Dim oOutlookApp As Outlook.Application
Dim oItem As Outlook.MailItem

On Error Resume Next

'Get Outlook if it's running
Set oOutlookApp = GetObject(, "Outlook.Application")
If Err <> 0 Then
    'Outlook wasn't running, start it from code
    Set oOutlookApp = CreateObject("Outlook.Application")
    bStarted = True
End If

'Create a new mailitem
Set oItem = oOutlookApp.CreateItem(olMailItem)

With oItem
    'Set the recipient for the new email
   .To = txtPara.Text
    'Set the recipient for a copy
    .CC = txtCC.Text
    'Set the subject
    .Subject = txtAsunto.Text
    'The content of the document is used as the body for the email
    .Body = ActiveDocument.Content
    .Attachments.Add ActiveDocument.Path & "\" & ActiveDocument.Name
    .Attachments.Add txtAdjuntos.Text
    '.BodyFormat = olFormatRichText
    .Send
End With

If bStarted Then
    'If we started Outlook from code, then close it
    oOutlookApp.Quit
End If

'Clean up
Set oItem = Nothing
Set oOutlookApp = Nothing

End Sub

Private Sub cmdAceptar_Click()
    If txtPara.Text = "" Then
      MsgBox "Introduzca la dirección de correo electrónico del destinatario"
    ElseIf txtAsunto.Text = "" Then
      MsgBox "Introduzca el asunto del correo electrónico"
    Else
      Me.Hide
      SendDocumentInMail
      frmBoton.Show
    End If
End Sub

Private Sub cmdCancelar_Click()
    Me.Hide
    frmBoton.Show
End Sub

Private Sub Label4_Click()

End Sub

Private Sub txtAdjuntos_Change()

End Sub

Private Sub UserForm_Activate()
    If txtPara.Text = "" Then
        If ActiveDocument.Bookmarks.Exists("correoDestinatario") Then
            ActiveDocument.Bookmarks("correoDestinatario").Select
            txtPara.Text = Selection.Text
        End If
    End If
    If txtAsunto.Text = "" Then
        txtAsunto.Text = ActiveDocument.BuiltInDocumentProperties(wdPropertySubject)
    End If
End Sub

Private Sub UserForm_QueryClose(Cancel As Integer, CloseMode As Integer)
    frmBoton.Show
End Sub
