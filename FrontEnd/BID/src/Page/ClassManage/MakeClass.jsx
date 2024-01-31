import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDownload } from "@fortawesome/free-solid-svg-icons";
import styled from "./MakeClass.module.css";
import { useCallback, useState } from "react";

export default function MakeClass() {
  const XLSX = require("xlsx");
  const [uploadedFile, setUploadedFile] = useState(null);

  const handleDrop = useCallback(async (acceptedFiles) => {
    if (acceptedFiles.length > 0) {
      const file = acceptedFiles[0];

      const reader = new FileReader();
      reader.onload = async (e) => {
        const data = new Uint8Array(e.target.result);
        const workbook = XLSX.read(data, { type: "array", bookVBA: true });

        const sheetName = workbook.SheetNames[0];
        const sheet = workbook.Sheets[sheetName];
        const jsonData = XLSX.utils.sheet_to_json(sheet);

        setUploadedFile({ file, jsonData });
      };

      reader.readAsArrayBuffer(file);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return (
    <section className={styled.makeClass}>
      <button className={styled.xlsxDownload}>
        <FontAwesomeIcon icon={faDownload} />
        엑셀 파일 다운로드
      </button>
      <div className={styled.makeInfo}>
        <span>
          엑셀 파일을 다운로드 받은 다음, 양식에 맞게 학생 정보를 입력해주세요
        </span>
        <span className={styled.info2}>
          완성된 엑셀 파일을 업로드 하면 학생 정보가 자동으로 등록돼요!
        </span>
      </div>
      <section>
        <input type="number" id="year" />
        <label htmlFor="year">학년</label>
        <input type="number" id="class" />
        <label htmlFor="class">반</label>
        <input
          type="file"
          accept=".xlsx, .xls, .csv"
          onChange={(e) => handleDrop(e.target.files)}
        />
        <section className={styled.viewStu}>
          {uploadedFile &&
            uploadedFile.jsonData.map((student) => (
              <div>
                <span>{student.번호}</span>
                <span>{student.생년월일}</span>
              </div>
            ))}
        </section>
      </section>
      <button className={styled.makeBtn}>학급 생성</button>
    </section>
  );
}
