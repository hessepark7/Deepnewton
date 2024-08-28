package com.example.deepnewton

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deepnewton.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var isIdUnique = false
    private var isNicknameUnique = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.btnCheckIdReg.setOnClickListener {
            val email = binding.editTextIdReg.text.toString()
            checkIdDuplicate(email)
        }

        binding.btnCheckNickReg.setOnClickListener {
            val nickname = binding.editTextNickReg.text.toString()
            checkNicknameDuplicate(nickname)
        }

        binding.btnRegisterReg.setOnClickListener {
            val email = binding.editTextIdReg.text.toString()
            val password = binding.editTextPassReg.text.toString()
            val confirmPassword = binding.editTextRePassReg.text.toString()
            val nickname = binding.editTextNickReg.text.toString()
            val phonenumber = binding.editTextPhoneReg.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || nickname.isEmpty() || phonenumber.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isIdUnique) {
                Toast.makeText(this, "아이디 중복확인을 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isNicknameUnique) {
                Toast.makeText(this, "닉네임 중복확인을 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val uid = user?.uid

                        if (uid != null) {
                            val userMap = hashMapOf(
                                "email" to email,
                                "password" to password,
                                "nickname" to nickname,
                                "phonenumber" to phonenumber
                            )

                            firestore.collection("id_list").document(uid)
                                .set(userMap)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "회원가입에 성공했습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "데이터베이스에 저장 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(this, "회원가입에 실패했습니다: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun checkIdDuplicate(email: String) {
        firestore.collection("id_list")
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result
                    isIdUnique = documents?.isEmpty == true
                    if (isIdUnique) {
                        Toast.makeText(this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "아이디 확인 중 오류 발생: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkNicknameDuplicate(nickname: String) {
        firestore.collection("id_list")
            .whereEqualTo("nickname", nickname)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result
                    isNicknameUnique = documents?.isEmpty == true
                    if (isNicknameUnique) {
                        Toast.makeText(this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "닉네임 확인 중 오류 발생: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
