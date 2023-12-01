package com.example.exercicio1;

import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioDAO {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addUsuario(Usuario usuario, Context context) {
        Map<String, Object> usuarioData = new HashMap<>();
        usuarioData.put("nome", usuario.getNome());
        usuarioData.put("cpf", usuario.getCpf());
        usuarioData.put("dataNascimento", usuario.getDataNascimento());
        usuarioData.put("telefone", usuario.getTelefone());
        usuarioData.put("email", usuario.getEmail());
        usuarioData.put("cep", usuario.getCep());
        usuarioData.put("senha", usuario.getSenha());
        usuarioData.put("rg", usuario.getRg());
        usuarioData.put("rua", usuario.getRua());
        usuarioData.put("numero", usuario.getNumero());
        usuarioData.put("bairro", usuario.getBairro());
        usuarioData.put("cidade", usuario.getCidade());
        usuarioData.put("complemento", usuario.getComplemento());


        db.collection("usuario").add(usuarioData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String usuarioId = documentReference.getId();
                        usuario.setId(usuarioId);
                        documentReference.update("id", usuarioId);
                        String mensagemSucesso = "Usuário Cadastrado!";
                        mostrarDialogo(context, "Sucesso", mensagemSucesso);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String mensagemErro = "Erro ao cadastrar usuário: " + e.getMessage();
                        mostrarDialogo(context, "ERRO", mensagemErro);
                    }
                });
    }

    public void editUsuario(Usuario usuario, Context context) {
        if (usuario.getId() != null) {
            Map<String, Object> usuarioData = new HashMap<>();
            usuarioData.put("nome", usuario.getNome());
            usuarioData.put("cpf", usuario.getCpf());
            usuarioData.put("dataNascimento", usuario.getDataNascimento());
            usuarioData.put("telefone", usuario.getTelefone());
            usuarioData.put("email", usuario.getEmail());
            usuarioData.put("cep", usuario.getCep());
            usuarioData.put("senha", usuario.getSenha());
            usuarioData.put("rg", usuario.getRg());
            usuarioData.put("rua", usuario.getRua());
            usuarioData.put("numero", usuario.getNumero());
            usuarioData.put("bairro", usuario.getBairro());
            usuarioData.put("cidade", usuario.getCidade());
            usuarioData.put("complemento", usuario.getComplemento());


            db.collection("usuario").document(usuario.getId()).set(usuarioData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String mensagemSucesso = "Usuário atualizado!";
                            mostrarDialogo(context, "Sucesso", mensagemSucesso);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String mensagemErro = "Erro ao atualizar usuário: " + e.getMessage();
                            mostrarDialogo(context, "ERRO", mensagemErro);
                        }
                    });
        } else {
            String mensagemErro = "ID do usuário não encontrado. Certifique-se de que o usuário foi cadastrado.";
            mostrarDialogo(context, "ERRO", mensagemErro);
        }
    }

    public void removeUsuario(String usuarioId, Context context) {
        if (usuarioId != null && !usuarioId.isEmpty()) {
            db.collection("usuario").document(usuarioId).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String mensagemSucesso = "Usuário excluído com sucesso!";
                            mostrarDialogo(context, "Sucesso", mensagemSucesso);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String mensagemErro = "Erro ao excluir usuário: " + e.getMessage();
                            mostrarDialogo(context, "ERRO", mensagemErro);
                        }
                    });
        } else {
            String mensagemErro = "ID do usuário inválido. Certifique-se de fornecer um ID válido.";
            mostrarDialogo(context, "ERRO", mensagemErro);
        }
    }

    // ... (existing code)

    public void getAllUsuarios(Context context, final UsuariosCallback callback) {
        List<Usuario> usuarios = new ArrayList<>();

        db.collection("usuario").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Usuario> usuarios = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Usuario usuario = new Usuario(
                            documentSnapshot.getString("nome"),
                            documentSnapshot.getString("dataNascimento"),
                            documentSnapshot.getString("telefone"),
                            documentSnapshot.getString("email"),
                            documentSnapshot.getString("senha")
                    );

                    usuario.setId(documentSnapshot.getId());
                    usuario.setCpf(documentSnapshot.getString("cpf"));
                    usuario.setCep(documentSnapshot.getString("cep"));
                    usuario.setRg(documentSnapshot.getString("rg"));
                    usuario.setRua(documentSnapshot.getString("rua"));
                    usuario.setNumero(documentSnapshot.getLong("numero").intValue());
                    usuario.setBairro(documentSnapshot.getString("bairro"));
                    usuario.setCidade(documentSnapshot.getString("cidade"));
                    usuario.setComplemento(documentSnapshot.getString("complemento"));

                    usuarios.add(usuario);
                }

                callback.onCallback(usuarios);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onCallback(null);
            }
        });
    }

    public void getUsuarioById(String id, Context context, GetUsuarioCallback callback) {
        db.collection("usuario").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario usuario = new Usuario(
                        documentSnapshot.getString("nome"),
                        documentSnapshot.getString("dataNascimento"),
                        documentSnapshot.getString("telefone"),
                        documentSnapshot.getString("email"),
                        documentSnapshot.getString("senha")
                );

                usuario.setId(documentSnapshot.getId());
                usuario.setCpf(documentSnapshot.getString("cpf"));
                usuario.setCep(documentSnapshot.getString("cep"));
                usuario.setRg(documentSnapshot.getString("rg"));
                usuario.setRua(documentSnapshot.getString("rua"));
                usuario.setNumero(documentSnapshot.getLong("numero").intValue());
                usuario.setBairro(documentSnapshot.getString("bairro"));
                usuario.setCidade(documentSnapshot.getString("cidade"));
                usuario.setComplemento(documentSnapshot.getString("complemento"));

                callback.onCallback(usuario);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onCallback(null);
            }
        });
    }

    public interface GetUsuarioCallback {
        void onCallback(Usuario usuario);
    }

    public interface UsuariosCallback {
        void onCallback(List<Usuario> usuarios);
    }

    private void mostrarDialogo(Context context, String titulo, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }
}
