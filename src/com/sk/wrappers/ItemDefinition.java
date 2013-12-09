package com.sk.wrappers;

import com.sk.wrappers.protocol.ArrayProtocol;
import com.sk.wrappers.protocol.BasicProtocol;
import com.sk.wrappers.protocol.ExtraAttributeReader;
import com.sk.wrappers.protocol.FieldExtractor;
import com.sk.wrappers.protocol.ProtocolGroup;
import com.sk.wrappers.protocol.extractor.ParseType;
import com.sk.wrappers.protocol.extractor.SizedStreamExtractor;
import com.sk.wrappers.protocol.extractor.StaticExtractor;

public class ItemDefinition extends ProtocolWrapper<ItemDefinitionLoader> {

	public boolean stackable;
	public boolean members;
	public boolean edible;
	public boolean noted;
	public boolean lent;
	public int slot = -1;
	public int noteId = -1;
	public int value = 0;
	public int lentId = -1;
	public String[] actions = { null, null, null, null, "Drop" };
	public String[] groundActions = { null, null, "Take", null, null };
	public int noteTemplateId = -1;
	public int lentTemplateId = -1;
	public int team;

	public String name;

	public ItemDefinition(ItemDefinitionLoader loader, int id) {
		super(loader, id, protocol);
	}

	private static final ProtocolGroup protocol = new ProtocolGroup();

	static {
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.INT, "value") }, 12)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.USHORT),
				new FieldExtractor(ParseType.USHORT) }, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109)
				.addSelfToGroup(protocol);
		new ExtraAttributeReader().addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.UBYTE, "team") }, 115)
				.addSelfToGroup(protocol);
		new ArrayProtocol(ParseType.UBYTE, new FieldExtractor[] {}, 40, 41, 42, 132).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.UBYTE, "slot") }, 13)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(new SizedStreamExtractor(0)) }, 65)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.STRING, "name") }, 2)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.BIG_SMART) }, 1, 23, 24, 25, 26, 78,
				79, 90, 91, 92, 93).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(new SizedStreamExtractor(4)) }, 43)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.STRING, "groundActions") }, 30, 31,
				32, 33, 34).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.USHORT) }, 4, 5, 6, 7, 8)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(new SizedStreamExtractor(3)) }, 125, 126, 127,
				128, 129, 130).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.USHORT, "lentTemplateId") }, 122)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(new SizedStreamExtractor(1)) }, 14, 27, 96,
				113, 114, 134).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(new SizedStreamExtractor(2)) }, 18, 44, 45,
				94, 95, 110, 111, 112, 139, 140, 142, 143, 144, 145, 146, 150, 151, 152, 153, 154)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.STRING, "actions") }, 35, 36, 37,
				38, 39).addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(new StaticExtractor(true), "stackable") }, 11)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(new StaticExtractor(true), "members") }, 16)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.USHORT, "lentId") }, 121)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.USHORT, "noteId") }, 97)
				.addSelfToGroup(protocol);
		new BasicProtocol(new FieldExtractor[] { new FieldExtractor(ParseType.USHORT, "noteTemplateId") }, 98)
				.addSelfToGroup(protocol);
	}

}
